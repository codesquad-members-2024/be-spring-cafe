package codesquad.springcafe.MemberTest;

import codesquad.springcafe.Member.Member;
import codesquad.springcafe.Member.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class MemoryMemberRepositoryTest {

    private MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    void setUp() {
        memoryMemberRepository = new MemoryMemberRepository();
    }

    @AfterEach
    void tearDown() {
        memoryMemberRepository.clear();
    }

    @Test
    @DisplayName("유저 추가 테스트")
    void addMemberTest() {
        Member member = new Member("user1", "user1Name", "user1@example.com", "password1");
        memoryMemberRepository.addMember(member);
        assertThat(memoryMemberRepository.getAllMembers()).contains(member);
    }

    @Test
    @DisplayName("멤버 조회 테스트")
    void getMemberTest() {
        Member member = new Member("user2", "user2Name", "user2@example.com", "password2");
        memoryMemberRepository.addMember(member);
        Optional<Member> foundMember = memoryMemberRepository.findById("user2");

        assertThat(foundMember.isPresent()).isTrue();
        assertThat(foundMember.get()).isEqualTo(member);
    }

    @Test
    @DisplayName("회원 정보 수정 성공 테스트")
    void updateMemberInfoSuccess() {
        Member originalMember = new Member("user3", "user3Name", "user3@example.com", "password3");
        memoryMemberRepository.addMember(originalMember);

        Member updatedMember = new Member("user3", "updatedName", "updated@example.com", "password3");
        memoryMemberRepository.updateMemberInfo(updatedMember);

        Member result = memoryMemberRepository.findById("user3").get();
        assertThat(result.getName()).isEqualTo("updatedName");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
    }

    @Test
    @DisplayName("비밀번호 불일치로 인한 회원 정보 수정 실패 테스트")
    void updateMemberInfoFail() {
        Member originalMember = new Member("user4", "user4Name", "user4@example.com", "password4");
        memoryMemberRepository.addMember(originalMember);

        Member updatedMember = new Member("user4", "updatedName", "updated@example.com", "wrongPassword");
        memoryMemberRepository.updateMemberInfo(updatedMember);

        Member result = memoryMemberRepository.findById("user4").get();
        assertThat(result.getName()).isEqualTo("user4Name");
        assertThat(result.getEmail()).isEqualTo("user4@example.com");
    }
}
