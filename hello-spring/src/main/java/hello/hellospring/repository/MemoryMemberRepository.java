package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //key값을 생성해주는 변수
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); //null이어도 반환이 가능하게 Optional.ofNullable
    }
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store.values값인 Member를 반환
    }
    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); //loop 다 돌려서 똑같은 이름이 있으면 반환
    }
    public void clearStore() {
        store.clear();
    }
}