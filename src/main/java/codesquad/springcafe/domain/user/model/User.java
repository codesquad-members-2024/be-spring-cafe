package codesquad.springcafe.domain.user.model;

import codesquad.springcafe.global.model.BaseTime;

import java.time.LocalDateTime;

// TODO: 빌더 패턴 사용
public class User extends BaseTime {
    private Long id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(createdAt, modifiedAt);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    // TODO: setId를 쓰지 않을 방법 찾기
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // TODO: 암호화 로직
    public String getPassword() {
        return password;
    }
}
