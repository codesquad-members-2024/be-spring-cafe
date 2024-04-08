package codesquad.springcafe.domain.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    @DisplayName("yelly라는 로그인 아이디를 갖는 멤버를 생성할 수 있다")
    @Test
    void create_member_success() {
        Member member = new Member("yelly", "123", "jelly", "yelly@test.com");

        assertThat(member.getLoginId()).isEqualTo("yelly");
        assertThat(member.getPassword()).isEqualTo("123");
        assertThat(member.getUserName()).isEqualTo("jelly");
        assertThat(member.getEmail()).isEqualTo("yelly@test.com");
    }
}