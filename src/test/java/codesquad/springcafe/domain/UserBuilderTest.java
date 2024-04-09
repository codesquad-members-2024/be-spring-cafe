package codesquad.springcafe.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserBuilderTest {

    @Test
    @DisplayName("빌더 패턴으로 User 객체가 생성되는지 테스트")
    void testBuild() {
        User user = new UserBuilder()
            .userId("userId")
            .email("email")
            .nickname("nickname")
            .password("password")
            .build();

        assertEquals("userId", user.getUserId());
        assertEquals("email", user.getEmail());
        assertEquals("nickname", user.getNickname());
        assertEquals("password", user.getPassword());
    }
}