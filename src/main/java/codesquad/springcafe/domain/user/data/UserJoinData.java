package codesquad.springcafe.domain.user.data;

import codesquad.springcafe.domain.user.model.User;

import java.beans.ConstructorProperties;

public class UserJoinData {
    private final String email;
    private final String name;
    private final String password;

    @ConstructorProperties({"email", "name", "password"})
    public UserJoinData(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public User toUser() {
        return new User(this.name, this.email, this.password);
    }

    @Override
    public String toString() {
        return "UserJoinData{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
