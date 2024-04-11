package codesquad.springcafe.controller.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UpdateMember {
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String beforePassword;
    @NotEmpty
    private String afterPassword;
    @NotEmpty
    private String userName;
    @NotEmpty
    @Email
    private String email;

    public UpdateMember() {
    }

    public String getLoginId() {
        return loginId;
    }

    public String getBeforePassword() {
        return beforePassword;
    }

    public String getAfterPassword() {
        return afterPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public UpdateMember setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public UpdateMember setBeforePassword(String beforePassword) {
        this.beforePassword = beforePassword;
        return this;
    }

    public UpdateMember setAfterPassword(String afterPassword) {
        this.afterPassword = afterPassword;
        return this;
    }

    public UpdateMember setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UpdateMember setEmail(String email) {
        this.email = email;
        return this;
    }
}
