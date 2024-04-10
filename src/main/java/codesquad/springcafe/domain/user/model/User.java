package codesquad.springcafe.domain.user.model;

// TODO: 빌더 패턴 사용
public class User{
    private Long id;
    private String name;
    private String email;
    private String password;

    public User(String name, String email, String password) {
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
