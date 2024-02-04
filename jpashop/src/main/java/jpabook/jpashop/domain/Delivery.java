package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    //EnumType의 기본값은 EnumType.ORDINAL인데 숫자로 변환되기 때문에 0(READY),1(COMP) 이었는데 중간에 값이 들어오면 로직이 망함(ex, 0, ???, 1)
    //그렇기 떄문에 ENUM을 사용할 때는 Enumerated를 해서 STRING으로 바꿔줘야한다. 몇 글자 아끼려다가 장애가 난다.
    private DeliveryStatus status; //READY, COMP
}
