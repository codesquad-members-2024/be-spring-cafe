package codesquad.springcafe.db.user;

import codesquad.springcafe.db.user.interfaces.DuplicationCheck;
import codesquad.springcafe.db.user.interfaces.UserModification;
import codesquad.springcafe.db.user.interfaces.UserQuery;

public interface UserDatabase extends UserModification, UserQuery, DuplicationCheck {

}
