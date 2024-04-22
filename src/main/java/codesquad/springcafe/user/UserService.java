package codesquad.springcafe.user;

import codesquad.springcafe.user.database.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findByUserId(String userId){
        return userRepository.findByUserId(userId);
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public void updateUser(String userId, User user) {
        userRepository.updateUser(user,userId);
    }

    public boolean checkUserExists(String userId, String password) {
        User user = userRepository.findByUserId(userId);
        if (user == null){
            return false;
        } else return user.getPassword().equals(password);
    }
}
