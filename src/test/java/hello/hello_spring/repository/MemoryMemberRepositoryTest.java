package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//import org.assertj.core.api; -> 이렇게 import해서 쓰려면 오류가 난다. 아래처럼 해야한다
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach  //메서드가 실행이 끝날때마다 어떤 동작을 하는..것 (이 실습에서는 save가 끝나면 afterEach호출, findAll이 끝나면 afterEach호출...)
    public void afterEach() {
        repository.clearStore();    //테스트가 실행되고 끝날 때마다 한번씩 저장소를 다 지운다.
        //이렇게 하면 테스트케이스 실행 순서와 상관이 없어진다 (오류 발생 안함)
        //테스트는 서로 의존관계 (순서 관계) 없이 문제없이 실행되도록 설계 되어야한다.
        //그러려면 공용 저장소나 데이터를 깔끔하게 지워줘야한다.
    }

    @Test   //org.junit.jupiter.api 요거를 import 해준다.
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        //방법1
        //System.out.println("result = " + (result == member));

        //방법2
        //Assertions.assertEquals(member, result);

        //Assertions.assertEquals(member, null);  //일부러 오류 나는거 확인해보는 용도. 이렇게 돌리면 아래처럼, 기대값과 실제값이 출력됨
        //org.opentest4j.AssertionFailedError:
        //Expected :hello.hello_spring.domain.Member@233fe9b6
        //Actual   :null

        //방법3
        assertThat(member).isEqualTo(result); //멤버가. result랑. 독같애. 라는 문법
        //assertThat(member).isEqualTo(null); //멤버가. result랑. 독같애. 라는 문법 -> 오류나는지 확인하는 라인
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();  //shift + f6 하면 rename 할수있음
        member2.setName("spring2");
        repository.save(member2);

//        String name1 = member1.getName(member1);
//        repository.findByName(name1);
//
//        String name2 = member1.getName(member1);
//        repository.findByName(name2);
        Member result = repository.findByName("spring1").get(); //뒷부분만 쓴다음에 ctrl alt v

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        //when
        List<Member> result = repository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);

    }
}
