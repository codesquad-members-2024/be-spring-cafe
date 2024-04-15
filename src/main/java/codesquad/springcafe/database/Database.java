package codesquad.springcafe.database;

import codesquad.springcafe.user.domain.User;

import java.util.List;

public interface Database {

    void addUser(User user);

    List<User> getUsers();
}
