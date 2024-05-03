package codesquad.springcafe.domain.user.dto;

import java.io.Serializable;

public class LoginUser implements Serializable {

    private String userId;
    private String password;

    public LoginUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }


}
