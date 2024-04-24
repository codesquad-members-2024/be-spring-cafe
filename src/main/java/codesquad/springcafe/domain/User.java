package codesquad.springcafe.domain;

public class User {
    private String id;
    private String nickname;
    private String email;
    private String password;

    public User(String id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }
    public String getId() {
        return id;
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

    // memoryUserDatabase에만 사용
    public void update(UpdatedUser updatedUser){
        this.nickname = updatedUser.getNewNickname();
        this.email = updatedUser.getNewEmail();

        if(!updatedUser.getNewPassword().isEmpty()){
            this.password = updatedUser.getNewPassword();
        }
    }

    @Override
    public String toString() {
        return "id: " + id + ", nickname: " + nickname + ", email: " + email;
    }
}
