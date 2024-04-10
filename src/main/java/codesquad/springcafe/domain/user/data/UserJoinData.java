package codesquad.springcafe.domain.user.data;

import codesquad.springcafe.domain.user.model.User;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

/**
 * 유저 회원가입 정보를 저장하는 데이터 클래스
 */
public class UserJoinData {
    private final String email;
    private final String name;
    private final String password;

    @ConstructorProperties({"email", "name", "password"})
    public UserJoinData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    /**
     * 저장된 속성 값을 활용해 User 객체를 생성합니다
     * <p>생성시간과 수정시간은 현재로 설정합니다
     * @return User 객체 생성해 반환
     */
    public User toUser() {
        return new User(this.name, this.email, this.password,
                LocalDateTime.now(), LocalDateTime.now());
    }
}
