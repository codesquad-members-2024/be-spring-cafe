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
    void tearDown() { memberRepository.clear();}
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
        Optional<Member> foundMember = memberRepository.findById("user2");

        assertThat(foundMember.isPresent()).isTrue();
        assertThat(foundMember.get()).isEqualTo(member);
    }
    @Test
    @DisplayName("회원 정보 수정 성공 테스트")
    void updateMemberInfoSuccess() {
        Member originalMember = new Member("user3", "user3Name", "user3@example.com", "password3");
        memberRepository.addMember(originalMember);

        Member updatedMember = new Member("user3", "updatedName", "updated@example.com", "password3");
        boolean result = memberRepository.updateMemberInfo("user3", updatedMember);

        assertThat(result).isTrue();
        assertThat(memberRepository.findById("user3").get().getName()).isEqualTo("updatedName");
        assertThat(memberRepository.findById("user3").get().getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    @DisplayName("비밀번호 불일치로 인한 회원 정보 수정 실패 테스트")
    void updateMemberInfoFail() {
        Member originalMember = new Member("user4", "user4Name", "user4@example.com", "password4");
        memberRepository.addMember(originalMember);

        Member updatedMember = new Member("user4", "updatedName", "updated@example.com", "wrongPassword");
        boolean result = memberRepository.updateMemberInfo("user4", updatedMember);

        assertThat(result).isFalse();
        assertThat(memberRepository.findById("user4").get().getName()).isEqualTo("user4Name");
        assertThat(memberRepository.findById("user4").get().getEmail()).isEqualTo("user4@example.com");
    }
}
