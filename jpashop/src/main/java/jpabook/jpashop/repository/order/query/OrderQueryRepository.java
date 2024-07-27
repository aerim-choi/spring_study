package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos() {
        // 루트 조회(toOne 코드를 모두 한번에 조회)
        List<OrderQueryDto> result = findOrders(); //Query 1번 -> N개

        // 스트림을 이용하여 컬렉션 추가(추가 쿼리 실행)
        return result.stream()
                .map(o -> {
                    List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); //Query N번
                    o.setOrderItems(orderItems);
                    return o;
                })
                .collect(Collectors.toList());
    }


    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi"+
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate,o.status,d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDtos_optimizition() {

        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = toOrderIds(result);

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);

        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;

    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
                .map(o->o.getOrderId())
                .collect(Collectors.toList());
        return orderIds;
    }

    //핵심 비즈니스를 위해서 엔티티를 찾을 땐 OrderRepository를 찾고 화면에 핏한 엔티티가 아닌 것을 찾을 떈 쿼리레포지토리에서 찾기
    //orderAPI 컨트롤러 안에 있는 Order DTO를 참조해버리게 되면 어떤 문제가 발생하냐면 레포지토리가 컨트롤러를 참조하는 의존관계가 순환이 되어버림
}
