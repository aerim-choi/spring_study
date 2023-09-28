package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;


    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository); //같은 메모리 레포지토리를 사용하기 위해서
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    //테스트코드는 한글로도 많이적음
    @Test
    void 회원가입(){
        //given : 상황이 주어졌을 떄
        Member member = new Member();
        member.setName("hello");

        //when : 이것으로 실행하면
        Long saveId = memberService.join(member);

        //then : 결과가 이것이 나와야함
        Member findMember=memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }
    @Test void 중복_회원_예외(){
        //given
        Member member1= new Member();
        member1.setName("spring");

        Member member2= new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        memberService.join(member1);
//        try{
//            memberService.join(member2);
//            fail();
//        }catch(IllegalStateException e){
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }
    @Test
    void findMembers(){

    }
    @Test
    void findOne(){

    }

}