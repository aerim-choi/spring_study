package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId,Long itemId, int count){

        //엔티티 조회
        Member member= memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item,item.getPrice(),count);
        //주문상품은 하나만 넘어가게 화면에서 제약해놓음

        //주문 생성
        Order order= Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);


        return order.getId();
    }
    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }
    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}

/**
 * JPA나 ORM을 사용한다면 도메인모델 패턴을 사용한다.
 * 뭐가 나쁘다 좋다는 아니다. 한프로젝트에서 트랜잭션 스크립트 패턴, 도메인 모델 패턴양립할 수 있다.
 * 현재 내 문맥에서 뭐가 맞는지 파악하고 사용하면 된다.
 * 뭐가 유지보수하기 쉬운지 생각하면 된다.
*/