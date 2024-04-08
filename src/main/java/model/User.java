package model;

public class User {
    private String email;   // 이메일
    private String userId;  // 유저 아이디 = 닉네임
    private String password;    // 비밀번호

    public User(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString(){
        return "email : " + email + ", userId : " + userId + ", password : " + password;
    }




}
