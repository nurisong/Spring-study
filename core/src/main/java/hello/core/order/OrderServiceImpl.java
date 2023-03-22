package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 구현체에도 의존하고 있음
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // 코드도 함께 변경해야 하니 OCP 위반
    // private DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 변경 -> NullPointException

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); // 아이디 찾기
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    
    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
