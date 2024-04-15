package codesquad.springcafe.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private final String email;
    private String nickname;
    private String password;
    private LocalDate joinDate;
    private Long id;

    public User(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.joinDate = LocalDate.now();
    }

    public boolean hasSameNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public boolean hasSameEmail(String email) {
        return this.email.equals(email);
    }


    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }

    public User update(String nickname, String password) {
        User updateUser = new User(email, nickname, password);
        updateUser.setJoinDate(joinDate);
        updateUser.setId(id);
        return updateUser;
    }

    public String getEmail() {
        return email;
    }


    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", joinDate=" + joinDate +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(email, user.email) && Objects.equals(nickname, user.nickname)
                && Objects.equals(password, user.password) && Objects.equals(joinDate, user.joinDate)
                && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname, password, joinDate, id);
    }
}
