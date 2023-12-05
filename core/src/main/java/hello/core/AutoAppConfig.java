package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        basePackages = "hello.core.member",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    //스프링은 수동빈이 자동빈보다 우선등록권을 가져 오버라이딩 되지만 스프링프레임워크는 중복 빈 등록을 지원하지 않는다.
    //스프링 부트는 같은 빈을 수동 빈 등록, 자동 빈 등록 시 충돌이 발생한다.=> 스프링 부트 에러를 발생한다.

    //수동빈 등록
//    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}