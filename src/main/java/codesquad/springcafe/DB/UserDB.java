package codesquad.springcafe.DB;

import codesquad.springcafe.Model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

}
