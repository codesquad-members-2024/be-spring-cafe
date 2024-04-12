package codesquad.springcafe.MemberTest;

import codesquad.springcafe.Member.Member;
import codesquad.springcafe.Member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;


public class MemberRepositoryTest {

    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepository();
    }

    @AfterEach
    void tearDown() {
        memberRepository = null;
    }

    @Test
    @DisplayName("유저 추가 테스트")
    void addMemberTest() {
        Member member = new Member("user1", "user1Name", "user1@example.com", "password1");
        memberRepository.addMember(member);
        assertThat(memberRepository.getAllMembers()).contains(member);
    }

    @Test
    @DisplayName("멤버 조회 테스트")
    void getMemberTest() {
        Member member = new Member("user2", "user2Name", "user2@example.com", "password2");
        memberRepository.addMember(member);
        Optional<Member> foundMember = memberRepository.getMember("user2");

        assertThat(foundMember.isPresent()).isTrue();
        assertThat(foundMember.get()).isEqualTo(member);
    }
}
