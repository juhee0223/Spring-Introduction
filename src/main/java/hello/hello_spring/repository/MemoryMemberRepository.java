package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{ //단축키 alt enter 누르면..implement methods 뜬다. 자동생성해줌!
    //자동생성이 된 것을 활용해서 본격 구현 하면 됨

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; //실무에서는 동시성 문제를 고려해서 atomic을 쓰지만,, 여기서는 패스

    @Override
    public Member save(Member member) {
        member.setId(++sequence);   //아이디를 시스템 상에서 설정해주는 부분
        store.put(member.getId(), member); //애초에 store라는 곳은 <Long, Member> 꼴로 되어있다.
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { //id를 기반으로 스토어에서 꺼내면 된다.
        return Optional.ofNullable(store.get(id)); //Optional.ofNullable로 감싸면, null이어도 클라이드측에서 무언가를 할수있다. 그건 나중에 배움.
    }

    @Override   //MemberRepository 인터페이스에 선언된 findByName 메서드를 현 구현클래스가 실제로 구현하고있다는 표시.
    //store에 있는 모든 회원 값들을 꺼내서(Stream으로 바꾸고),
    //그 중에서 이름이 name과 같은 회원들만 필터링한 다음,
    //그 중 아무 회원이나 하나를 찾아서 Optional<Member>로 감싸서 반환
    public Optional<Member> findByName(String name) {   //Optional<T>는 “값이 있을 수도 있고, 없을 수도 있다”는 것을 표현하는 컨테이너 타입
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    //문법 설명:
    //(1) Map 인터페이스의 메서드 .values() => Map<Long, Member>에서 값(value) 컬렉션만 뽑아낸다
        //타입: Collection<Member>  즉, 지금 store.values()는
        //“저장소에 들어 있는 모든 Member 객체들의 모음”이다
    //(2) Collection 인터페이스의 기본 메서드 .stream() => 컬렉션을 Stream으로 바꾼다.
        //타입: Stream<Member>
        //Stream = “데이터들을 하나씩 흘려보내면서 중간 연산(filter, map 등)을 적용하고, 마지막에 결과를 얻는 파이프라인”이라고 생각하면 된다.
    //(3) filter 메서드 = Stream의 중간 연산
        //형식: Stream<T> filter(Predicate<? super T> predicate)
        //조건을 만족하는 요소만 통과시키는 역할을 한다.
    //여기서는 T가 Member이므로 Predicate<Member> = (Member -> boolean) 형태의 함수를 받는다.
    //(4) 람다식 member -> member.getName().equals(name)
        //람다 표현식 = 익명 함수를 짧게 적는 문법이다.
        //입력으로 member(Member 타입 하나)를 받아서 member.getName().equals(name) 결과(boolean)를 반환하는 함수
        //자바가 Stream<Member>를 알고 있으므로 member의 타입을 자동으로 Member로 추론한다
        //member.getName() : 각 회원의 이름
        //.equals(name) : 메서드 인자로 받은 name과 같은지 비교
            //결국 조건: “회원 이름이 인자로 받은 name과 같은가?”
            //따라서 filter( … )를 지나면
            //→ 해당 이름을 가진 회원들만 남은 Stream<Member> 가 된다.
    //(5) .findAny();
        // Stream의 종단 연산(terminal operation) 중 하나.
        //설명: “Stream 안에서 아무거나 한 요소를 찾아서 Optional에 싸서 돌려준다.”
        //타입: Optional<Member>
        //왜 Optional인가?
        //조건을 만족하는 회원이 한 명도 없을 수도 있기 때문이다.
        //그럴 경우 Optional.empty() 를 반환한다.




    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        //ArrayList 생성자 중 컬렉션을 받아서 복사하는 생성자를 사용한 것
        //즉, store.values()에 있는 것들을 기반으로 새로운 ArrayList를 만든다.
        //이 생성자의 역할:
        //매개변수로 받은 Collection의 요소들을 새 리스트에 그대로 복사
        //반환된 리스트는 Map에 직접 연결되지 않은 독립적인 객체 -> 외부는 반환된 리스트를 수정하더라도 store 내부 Map에 영향 없음

        //원래는
        //new ArrayList<Member>(store.values());
        //을 써야 했지만, 컴파일러가 자동 추론하므로 <Member> 생략 가능
    }

    public void clearStore() {
        store.clear();  //이러면 store를 싹 비운다.
    }
}
