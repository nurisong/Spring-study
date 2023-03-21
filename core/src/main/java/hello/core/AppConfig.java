package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class AppConfig {

    @Bean
    public MemberService memberService() {
        // return new MemberServiceImpl(new MemoryMemberRepository()); // 문제점: 역할에 따른 구현이 한눈에 드러나지
        return new MemberServiceImpl(memberRepository()); // c+a+M
        // 생성자 주입
    }

    @Bean
    private static MemberRepository memberRepository() { // 반환형은 인터페이스
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        // return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FIxDiscountPolicy();
        return new RateDiscountPolicy();
    }

    // 리펙토링 후 -> 중복제거, "역할"과 "구현"클래스가 한눈에 다 드러남 -> 애플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악 가능
    // 사용 영역과 구성 영역으로 나눠짐
}
