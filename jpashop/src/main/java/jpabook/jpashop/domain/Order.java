package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    //연관관계 주인은 그냥 냅두면 된다. (다른 테이블의 FK를 갖고있는 테이블)
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems= new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    //1대1관계는 어느쪽에 FK를 두는지 고민사항인데 강사님은 자주 접근하는 곳에 FK를 둔다.
    //그래서 Order를 자주 접근하기 때문에 FK를 둔다.(연관관계의 주인)
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;// 주문상태 [ORDER, CANCEL]

}
