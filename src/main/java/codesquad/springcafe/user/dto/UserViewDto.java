package codesquad.springcafe.user.dto;

import codesquad.springcafe.user.User;

public class UserViewDto {
    private Long index;
    private String userId;
    private String nickName;
    private String email;

    private UserViewDto(String userId, String nickName, String email) {
        this.userId = userId;
        this.nickName = nickName;
        this.email = email;
    }

    public static UserViewDto toProfileDto(User user) {
        return new UserViewDto("", user.getNickName(), user.getEmail());
    }

    public static UserViewDto toDto(User user) {
        return new UserViewDto(user.getUserId(), user.getNickName(), user.getEmail());
    }

    public void numbering(Long index) {
        this.index = index;
    }
}
