package codesquad.springcafe.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String nickname;
    private String email;
    private String password;
    private List<Article> articles = new ArrayList<Article>();

    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}