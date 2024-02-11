package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속된 클래스 한테이블에 때려박는다.
@DiscriminatorColumn(name = "dtype")

public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items",fetch = FetchType.LAZY)
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    /**
    * stock증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    /**
    * stock감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock <0 ){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
    //데이터를 가지고 있는 곳에 비즈니스 메서드를 가지고 있는게 좋다. stock 감소,증가는 Item클래스에 있는 것이 좋다.
    //변경할 일이 있으면 setter를 이용해 바깥에서 변경하는게 아니다. 이것이 가장 객체지향적인 것이다.
}
