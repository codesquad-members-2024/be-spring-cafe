package codesquad.springcafe.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCrednetialService {

    private final UserDatabase userDatabase;

    @Autowired
    public UserCrednetialService(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public boolean checkValidCredential(UserLoginDTO userLoginDTO) {
        if (!userDatabase.isExistUser(userLoginDTO.getUserid()) ||
                !userLoginDTO.isMatchedPwd(userDatabase.getUser(userLoginDTO.getUserid()).getPassword())) {
            return false;
        }
        return true;
    }
}
