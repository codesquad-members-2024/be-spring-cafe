package codesquad.springcafe.domain.user.model;

import codesquad.springcafe.global.model.BaseTime;

// TODO: 빌더 패턴 사용
public class User extends BaseTime {
    private Long id;
    private String loginId; //unique
    private String name;
    private String email;
    private String password;

    public User() {

    }

    public User(String loginId, String name, String email, String password) {
        super();
        this.loginId = loginId;
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

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User update(String name, String email) {
        this.name = name;
        this.email = email;
        return this;
    }
}
