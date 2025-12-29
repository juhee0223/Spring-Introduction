//회원 서비스를 만들려면 회원 리포지토리가 있어야겠죠
package hello.hello_spring.service;

import hello.hello_spring.domain.Member;    //import 안돼있어서 빨간줄 뜬다면 alt enter 치면됨
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service 애노테이션이 있으면 스프링 빈으로 자동 등록된다. 어노테이션이 있는 버전과, 직접 스프링빈 등록하는 버전 둘다 배운다.

public class MemberService {    //테스트할때 쉽게 하는 법 클래스에다가 ctrl shift T 하면 됨 (create new Test 라고 뜸. 누르면 자동으로 만들어짐)
    public final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {   //회원가입은 단순히 save 호출해주면 됨
/*      //   같은 이름이 있는 중복 회원 X (이번 실습에서 우리가 임의로 정한 비즈니스 로직임)
        //memberRepository.findByName(); 이렇게 친 상태에서 (컨트롤 알트 v)하면 자동으로 리턴형식 아래라인처럼 만들어준다.
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        //단, 위에 짜둔것처럼 Optional result로 받아오는게 그닥 예쁜 코딩이 아님. 그래서 아래처럼
        memberRepository.findByName(member.getName())   //여기까지가 곧 Optional member니까 바로 받아서 .ifPresent 가능
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                }); //그런데 이런거는 함수로 만들어서 쓰는게 좋다! (자주 쓸거라)*/

        validateDuplicateMember(member);    //중복회원 검증
        memberRepository.save(member);  //검증 통과했으면 저장하는것
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())   //여기까지가 곧 Optional member니까 바로 받아서 .ifPresent 가능
        .ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }); //함수로 뽑는 단축키는 ctrl alt shift T & extract method 선택하기
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
