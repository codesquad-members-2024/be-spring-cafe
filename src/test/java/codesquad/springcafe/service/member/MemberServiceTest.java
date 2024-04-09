package codesquad.springcafe.service.member;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.repository.member.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clear() {
        memberRepository.clear();
    }

    @DisplayName("yelly 라는 아이디로 회원가입을 할 수 있다")
    @Test
    void join() {
        // given
        Member member = new Member("yelly", "123", "yelly jelly", "yelly@test.com");

        // when
        Member joinedMember = memberService.join(member);

        // then
        assertThat(joinedMember.getLoginId()).isEqualTo("yelly");
        assertThat(joinedMember.getPassword()).isEqualTo("123");
        assertThat(joinedMember.getUserName()).isEqualTo("yelly jelly");
        assertThat(joinedMember.getEmail()).isEqualTo("yelly@test.com");
    }

    @DisplayName("두 번째로 회원가입한 ghost 아이디를 가진 멤버를 찾을 수 있다")
    @Test
    void getMember() {
        // given
        Member member1 = new Member("yelly", "123", "yelly jelly", "yelly@test.com");
        Member member2 = new Member("ghost", "123", "ghost", "ghost@test.com");
        memberService.join(member1);
        memberService.join(member2);

        // when
        Optional<Member> findMember = memberService.findMember(2L);

        // then
        assertThat(findMember).isPresent();
        assertThat(findMember.get()).extracting("loginId").isEqualTo("ghost");
    }

    @DisplayName("회원가입 되어있는 멤버 2명의 아이디 yelly, ghost 를 찾을 수 있다")
    @Test
    void findAllMember() {
        // given
        Member member1 = new Member("yelly", "123", "yelly jelly", "yelly@test.com");
        Member member2 = new Member("ghost", "123", "ghost", "ghost@test.com");
        memberService.join(member1);
        memberService.join(member2);

        // when
        List<Member> allMember = memberService.findAllMember();

        // then
        assertThat(allMember.size()).isEqualTo(2);
        assertThat(allMember).extracting("loginId").contains("yelly", "ghost");
    }
}