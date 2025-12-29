package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Member 객체를 저장하는 저장소 역할

public interface MemberRepository { //인터페이스 선언- 추상 메서드들의 집합. 구현은 없고 메서드 시그니처만 존재
    //아래의 기능들을 가진 저장소가 필요하다라는.... 계약 정의.
    //추후에 implements 키워드로 구현 클래스 작성

    //회원이 저장소에 저장됨 (회원가입 시 사용)
    //Member 객체를 받아서 저장한 후, 저장된 결과를 다시 반환
    Member save(Member member);

    //Null 처리 안전성을 위해 optional 사용. 값이 존재하면 Optional 안에 Member 존재
    //값이 없으면 Optional.empty() -> NullPointerException 방지
    Optional<Member> findById(Long id); //이걸로 회원을 찾아올수있음 (로그인, 회원 상세 정보 조회)
    Optional<Member> findByName(String name);  //이걸로 회원을 찾아올 수 있음 (중복 회원 체크 시에 사용가능)
    List<Member> findAll(); //지금까지 저장된 모든 회원 리스트를 반환해줌 (전체 조회)

}
