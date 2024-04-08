package codesquad.springcafe.Service;

import codesquad.springcafe.Domain.User;
import codesquad.springcafe.Repository.UserRepository;
import codesquad.springcafe.Repository.UserRepositoryImpl;

public class UserService {
    private final UserRepository userRepository;
    public UserService(){
        userRepository = new UserRepositoryImpl();
    }
    public void signUp(User user) {

    }
}
