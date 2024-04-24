package codesquad.springcafe.db.user.interfaces;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;


public interface UserModification {

    public void addUser(User user);

    public void update(String userId, UserProfileEditDto userProfileEditDto);

    public void clearDatabase();
}
