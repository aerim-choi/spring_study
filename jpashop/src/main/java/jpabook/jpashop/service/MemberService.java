package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//읽기에는 가급적으로 readOnly=true를 넣어줘야한다.
@Transactional(readOnly = true) //데이터 변경은 Transaction이 필요하다. public메소드에 기본적으로 transation이 들어간다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository; //필드 인젝션 =>접근할 수 가 없음 그래서 setter인젝션


    //회원가입
    @Transactional //따로 설정한 것은 우선권을 가진다. 기본적으로 readonly=false임
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        //여러 사용자가 동시에 회원가입 한다면 데이터베이스에 name을 UNIQUE조건을 걸어서 더 안전하게 할 수 있다.
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
