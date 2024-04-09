package codesquad.springcafe.repository.member;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired private MemberRepository repository;

    @BeforeEach
    void clear() {
        repository.clear();
    }

    @DisplayName("로그인 아이디 yelly로 회원가입 할 수 있다")
    @Test
    void join_success() {
        // given
        Member member = new Member("yelly", "123", "yelly", "yelly@test.com");

        // when
        Member savedMember = repository.save(member);

        // then
        assertThat(savedMember).isEqualTo(member);
        assertThat(savedMember.getLoginId()).isEqualTo("yelly");
    }

    @DisplayName("2명의 유저가 회원가입 시 고유 아이디는 1부터 1씩 증가한다")
    @Test
    void join_check_id() {
        // given
        Member member1 = new Member("yelly", "123", "yelly", "yelly@test.com");
        Member member2 = new Member("ghost", "123", "ghost", "ghost@test.com");

        // when
        Member savedMember1 = repository.save(member1);
        Member savedMember2 = repository.save(member2);

        // then
        assertThat(savedMember1.getId()).isEqualTo(1L);
        assertThat(savedMember1.getLoginId()).isEqualTo("yelly");
        assertThat(savedMember2.getId()).isEqualTo(2L);
        assertThat(savedMember2.getLoginId()).isEqualTo("ghost");
    }

    @DisplayName("yelly 라는 아이디를 가진 멤버를 찾을 수 있다")
    @Test
    void findById_success() {
        // given
        Member member = new Member("yelly", "123", "yelly jelly", "yelly@test.com");
        repository.save(member);

        // when
        Optional<Member> findMember = repository.findById("yelly");

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get()).extracting("loginId").isEqualTo("yelly");
    }

    @DisplayName("존재하지 않는 멤버를 찾으면 빈 Optional 을 반환한다.")
    @Test
    void findById_fail() {
        // when
        Optional<Member> findMember = repository.findById("yelly");

        // then
        assertThat(findMember).isEmpty();
    }

    @DisplayName("모든 회원 아이디 yelly, ghost 를 찾을 수 있다")
    @Test
    void findAll() {
        Member member1 = new Member("yelly", "123", "yelly jelly", "yelly@test.com");
        Member member2 = new Member("ghost", "123", "ghostuser", "ghost@test.com");
        repository.save(member1);
        repository.save(member2);

        // when
        List<Member> members = repository.findAll();

        // then
        assertThat(members).extracting("loginId").containsExactly("yelly", "ghost");
    }
}