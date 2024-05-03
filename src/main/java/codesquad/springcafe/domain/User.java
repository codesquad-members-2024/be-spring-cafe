package codesquad.springcafe.domain;

public class User {
    private int id;
    private String userId;
    private String nickname;
    private String email;
    private String password;

    // UserCreateDto를 통해 사용자가 입력한 User을 저장할 때 사용
    public User(String userId, String nickname, String email, String password) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    // h2에서 User 값을 가져올 때 사용하는 생성자
    public User(Integer id, String userId, String nickname, String email, String password) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
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
        return "userId: " + userId + ", nickname: " + nickname + ", email: " + email;
    }
}
