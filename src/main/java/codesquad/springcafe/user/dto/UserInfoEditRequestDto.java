package codesquad.springcafe.user.dto;

import codesquad.springcafe.user.User;
import jakarta.validation.constraints.NotEmpty;

public class UserInfoEditRequestDto {
    @NotEmpty(message = "기존 패스워드가 입력되지 않았습니다")
    private String password;
    @NotEmpty(message = "신규 패스워드가 입력되지 않았습니다")
    private String newPassword;
    @NotEmpty(message = "바꿀 닉네임이 입력되지 않았습니다")
    private String nickName;
    @NotEmpty(message = "바꿀 이메일이 입력되지 않았습니다")
    private String email;

    public UserInfoEditRequestDto(String password, String newPassword, String nickName, String email) {
        this.password = password;
    }
}
