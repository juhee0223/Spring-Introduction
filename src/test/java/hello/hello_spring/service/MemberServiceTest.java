package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;
    @BeforeEach
    public void beforeEach() {  //테스트 실행마다 새로 생성된다.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);    //Dependency Injection (DI)
        //서비스 객체가, 자신이 의존하는 리포지토리 객체의 구현체를 직접 생성하지 않고, 외부(설정 등)에서 전달받아 사용하는 설계 방식
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

//    @Test
//    void join() {   //테스트 메서드는 영어권 사람과 일하는게 아니라면, 한글로 이름 바꾸는 경우가 많다 (직관적으로 확인가능해서)
//    }

    @Test
    void 회원가입() {
        //given (뭔가가.또는 데이터가. 주어졌는데)
        //when (이걸 실행 했을때)
        //then (결과가 어떻게 되느냐. 를 보는게 테스트다)

        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); //alt enter로 static import

    }

    @Test   //테스트는 정상 플로우가 중요하지만, 예외플로우가 훨씬 더 중요하다 (중복회원 예외처리가 잘 동작하는지 확인해야함)
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");  //일부러 중복이름으르 설정한다

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //앞쪽 예외가 정상적으로 터지면 뒷쪽 람다를 실행한다.는 뜻

//        assertThrows(NullPointerException.class, () -> memberService.join(member2));
//        org.opentest4j.AssertionFailedError: Unexpected exception type thrown,
//        Expected :class java.lang.NullPointerException
//        Actual   :class java.lang.IllegalStateException


//        //이렇게 try-catch로 잡는건 좀 애매..해서 다른 방법도 알려줄게요.
//        try {
//            memberService.join(member2);
//            fail("이 라인으로 넘어오면 안됩니다. 예외가 발생해야합니다.");
//        } catch (IllegalStateException e) { //catch부로 오면 성공인것.
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }

        //then
    }

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {
        //given

        //when

        //then
    }
}