package codesquad.springcafe.user;

import java.util.List;

public interface UserDatabase {

     void addUser(User user);

     List<User> getUserList();

     User getUser(String userId);

     boolean isExistUser(String userId);
}
