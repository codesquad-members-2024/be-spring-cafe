package codesquad.springcafe.user;

public class UserViewDto {
    private long index;
    private String userId;
    private String nickName;
    private String email;

    public UserViewDto(long index, String userId, String nickName, String email) {
        this.index = index;
        this.userId = userId;
        this.nickName = nickName;
        this.email = email;
    }

    public UserViewDto(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

}
