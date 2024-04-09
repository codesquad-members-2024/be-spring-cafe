package codesquad.springcafe.domain.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Objects;

public class Member {
    private long id;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String userName;
    @NotEmpty
    @Email
    private String email;

    public Member() {
    }

    public Member(String loginId, String password, String userName, String email) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public Member setId(long id) {
        this.id = id;
        return this;
    }

    public Member setLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public Member setPassword(String password) {
        this.password = password;
        return this;
    }

    public Member setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Member setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member member)) {
            return false;
        }
        return getId() == member.getId() && Objects.equals(getLoginId(), member.getLoginId())
                && Objects.equals(getPassword(), member.getPassword()) && Objects.equals(getUserName(),
                member.getUserName()) && Objects.equals(getEmail(), member.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLoginId(), getPassword(), getUserName(), getEmail());
    }
}
