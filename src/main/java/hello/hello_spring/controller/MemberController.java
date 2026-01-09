package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;                            //도메인 객체 (회원 엔티티)
import hello.hello_spring.service.MemberService;                    //비즈니스 로직 담당 계층
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller     //@Controller 이 있으면 스프링컨테이너에 빈으로 자동 등록된다 & 이 클래스가 웹 요청을 처리하는 컨트롤러임을 스프링에 알립니다.
public class MemberController {

    private final MemberService memberService;  //컨트롤러는 직접 로직을 처리하지 않음, 회원 관련 비즈니스 로직은 MemberService에게 위임
    //final: 생성자를 통해서만 주입받겠다는 의미 & 변경 불가능 → 안정적인 설계

    /**
     * 스프링이 애플리케이션 시작 시:
     * 1. MemberController 생성
     * 2. 생성자 파라미터인 MemberService 타입의 빈을 찾음
     * 3. 찾아서 자동으로 주입
     *
     * 생성자 주입: 스프링에서 권장하는 DI 방식 & 테스트 용이 & 의존성 누락 시 컴파일/실행 초기에 바로 오류 발생
     * @param memberService
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * HTTP GET /members/new 요청 처리
     * 회원 가입 폼 화면 요청
     * 반환값: "members/createMemberForm" Thymeleaf 템플릿 이름
     *
     * 흐름:
     * 1. 브라우저 요청
     * 2. → Controller
     * 3. → View Resolver
     * 4. → HTML 렌더링
     */
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    /**
     * HTTP POST /members/new
     * 회원 가입 폼 제출 처리
     *
     * MemberForm form
     * HTML <input name="name"> 같은 값이 MemberForm 객체에 자동 바인딩(매핑)됨
     * @param form
     * @return
     */

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        //Controller: 요청 처리 웹 계층 객체(MemberForm) → 도메인 객체(Member) Domain: 비즈니스 데이터
        member.setName(form.getName());

        memberService.join(member); //실제 회원 가입 로직 실행
        // HTML input(name)
        // → POST 요청
        // → Spring이 MemberForm 생성
        // → setName() 호출
        // → Controller에서 form.getName()
        // → Member 생성
        // → Service로 전달
        return "redirect:/"; //회원 가입 후 홈 화면으로 이동
        //redirect:를 쓰는 이유:
        //새로고침 시 중복 POST 방지, PRG(Post-Redirect-Get) 패턴
    }

/*    GET /members/new	회원 가입 폼 화면을 보여줌
    POST /members/new	폼에서 입력한 데이터를 서버로 전송
    URL은 동일하지만, GET이냐, POST냐에 따라 동작이 다르다는게 포인트*/

/*    핵심 포인트 5개
    URL이 같아도 GET / POST는 완전히 다른 요청
    POST는 데이터를 body에 실어 보낸다
    input name ↔ Java 필드명이 1:1 매칭
    Spring은 setter로만 값 주입
    Form 객체는 웹 계층 전용 DTO다*/

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}



