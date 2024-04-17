package codesquad.springcafe.repository.member;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.controller.member.UpdateMember;
import codesquad.springcafe.domain.member.Member;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "test")
@JdbcTest
@Import(MemberRepositoryH2.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepositoryH2Test {

    @Autowired
    private MemberRepository repository;

    @AfterEach
    void reset_pk() {
        repository.clear();
    }

    @DisplayName("yelly라는 멤버를 최초로 추가하면 id는 1 이다")
    @Test
    void save() {
        // given
        Member member = new Member();
        member.setLoginId("yelly");
        member.setUserName("jelly");
        member.setPassword("123");
        member.setEmail("yelly@test.com");

        // when
        Member savedMember = repository.save(member);

        // then
        assertThat(savedMember.getId()).isEqualTo(1L);
        assertThat(savedMember.getLoginId()).isEqualTo("yelly");
        assertThat(savedMember.getPassword()).isEqualTo("123");
        assertThat(savedMember.getUserName()).isEqualTo("jelly");
        assertThat(savedMember.getEmail()).isEqualTo("yelly@test.com");
    }

    @DisplayName("yelly 라는 아이디를 가진 멤버를 찾을 수 있다")
    @Test
    void findById() {
        // given
        Member member = new Member();
        member.setLoginId("yelly");
        member.setUserName("jelly");
        member.setPassword("123");
        member.setEmail("yelly@test.com");

        repository.save(member);

        // when
        Optional<Member> optionalMember = repository.findById("yelly");

        // then
        assertThat(optionalMember).isPresent();
        assertThat(optionalMember.get().getId()).isEqualTo(1L);
        assertThat(optionalMember.get().getLoginId()).isEqualTo("yelly");
        assertThat(optionalMember.get().getPassword()).isEqualTo("123");
        assertThat(optionalMember.get().getUserName()).isEqualTo("jelly");
        assertThat(optionalMember.get().getEmail()).isEqualTo("yelly@test.com");
    }

    @DisplayName("모든 유저인 yelly, guest 2명을 찾을 수 있다")
    @Test
    void findAll() {
        // given
        Member member = new Member();
        member.setLoginId("yelly");
        member.setUserName("jelly");
        member.setPassword("123");
        member.setEmail("yelly@test.com");

        Member guest = new Member();
        guest.setLoginId("guest");
        guest.setUserName("guest user");
        guest.setPassword("asdf");
        guest.setEmail("guest@test.com");

        Member yelly = repository.save(member);
        Member guestUser = repository.save(guest);

        // when
        List<Member> members = repository.findAll();

        // then
        assertThat(members).hasSize(2);
        assertThat(members).contains(yelly, guestUser);
    }

    @DisplayName("닉네임을 yelly에서 guest로, 패스워드와 이메일을 각각 asdf, guest@test.com 으로 변경할 수 있다")
    @Test
    void update() {
        // given
        Member member = new Member();
        member.setLoginId("yelly");
        member.setUserName("jelly");
        member.setPassword("123");
        member.setEmail("yelly@test.com");

        repository.save(member);

        UpdateMember updateMember = new UpdateMember();
        updateMember.setLoginId("yelly");
        updateMember.setAfterPassword("asdf");
        updateMember.setUserName("guest user");
        updateMember.setEmail("guest@test.com");

        // when
        repository.update("yelly", updateMember);
        Member findMember = repository.findById("yelly").get();

        // then
        assertThat(findMember.getId()).isEqualTo(1L);
        assertThat(findMember.getLoginId()).isEqualTo("yelly");
        assertThat(findMember.getPassword()).isEqualTo("asdf");
        assertThat(findMember.getUserName()).isEqualTo("guest user");
        assertThat(findMember.getEmail()).isEqualTo("guest@test.com");
    }
}