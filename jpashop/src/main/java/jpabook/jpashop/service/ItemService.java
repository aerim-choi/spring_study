package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    public final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId,String name, int price,int stockQuantity ){
        /*
        트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고 , 엔티티의 데이터를 직접 변경하세요
        트랜잭션 커밋 시점에 변경감지가 실행됩니다.
        * */
        Item findItem = itemRepository.findOne(itemId);

        //의미있는 메소드 change메소드 같이 만들어야함(set 말고)
        //findItem.change(price, name, stockQuantiy);

        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
        return findItem;
    }
    /*
    1. 변경감지
    영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
    트랜 잭션 안에서 엔티티를 다시 조회, 변경할 값 선택 -> 트랜잭션 커밋 시점에 변경 감지(Dirty Cecking)이 동작해서
    데이터 베이스에 UPDATE SQL실행
    2. Merge(병합)
    updateItem() 코드를 em.merge(item); 한줄로 끝남
    주의 : 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만,
    병합을 사용하면 모든 속성이 변경된다. 병합시 값이 없으면 NULL로 업데이트 할 위헙이 있다.
    (병합은 모든 필드를 교체한다.)
    예를 들어 merge는 setPrice를 안하면 가격이 NULL로 되어버림
    !실무에서는 불편하더라도 모든 필드를 변경감지를 하는게 좋다.!

    가장 좋은 방법은 엔티티를 변경할 때는 항상 변경감지를 사용하자!!

     */
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}

