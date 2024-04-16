package codesquad.springcafe.domain;

import java.util.Objects;

public class User {
    private String userId;
    private String nickname;
    private String email;
    private String password;

    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }
    public String getNickname() {
        return nickname;
    }
    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }

    public boolean comparePassword(String inputPassword){
        return password.equals(inputPassword);
    }

    public void update(UserUpdate userUpdate){
        if(!userUpdate.getNewNickname().isEmpty()){
            this.nickname = userUpdate.getNewNickname();
        }
        if(!userUpdate.getNewEmail().isEmpty()){
            this.email = userUpdate.getNewEmail();
        }
        if(!userUpdate.getNewPassword().isEmpty()){
            this.password = userUpdate.getNewPassword();
        }
    }

    @Override
    public String toString() {
        return "userId: " + userId + ", nickname: " + nickname + ", email: " + email;
    }
}
