package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    //왜 Member 말고 MemberForm을 씀? 실제로 원하는 속성들이 다르기 때문임, 필요한 데이터만 채워서 넘기는 것을 권장(DTO사용)
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address =new Address(form.getCity(),form.getStreet(),form.getZipcode());

        Member member= new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }
    /*엔티티는 순수하게 유지해야한다. DTO를 사용해야한다!
    엔티티가 화면 관련한 코드에 종속적이면 안된다. 핵심비즈니스 로직을 유지보수 하기 어려워진다.
    DTO로 변환해서 화면에 필요한것만 보여줄것만 반환하는 것을 추천함 여기서는 손댈게 없기때무에 Member엔티티 반환한것임
    API를 만들때는 절대로 엔티티를 외부로 반환해서는 안된다!!!!!!API는 스펙임
    문제점1 엔티티의 비밀번호가 있으면 비밀번호가 노출되고
    문제점2 API 스펙이 변함 , 불완전한 API가 됨
    템플릿 엔진에서는 선택적으로 사용되기 때문에 서버 사이드 렌더링 하기 때문에 그래도 DTO를 사용하는 것이 제일 깔끔함
     */
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members =memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
