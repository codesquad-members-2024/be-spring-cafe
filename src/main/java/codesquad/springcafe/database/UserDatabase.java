package codesquad.springcafe.database;

import codesquad.springcafe.User;

import java.util.List;

public interface UserDatabase {

     void addUser(User user);

     List<User> getUserList();

     User getUser(String userId);
}
