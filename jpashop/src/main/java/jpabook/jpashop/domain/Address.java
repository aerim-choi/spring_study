package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address()  {}
    //Address같은 값타입은 변경이 불가능해야한다. Setter를 두지말자, 생성자만 냅두자
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}

//테이블 생성되는 DDL스크립트를 참고하는 것은 괜찮은데 그대로 쓰면 안되고 디테일하게 수정하고 정제해야 할 것들이 있다.
//강사님은 스크립트를 쭉 뽑아서 수정하고 정제한다고 한다.
