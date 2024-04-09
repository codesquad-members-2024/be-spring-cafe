package codesquad.springcafe.service;

import codesquad.springcafe.database.UserRepository;
import codesquad.springcafe.model.User;
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
    public User createUser(User user){
        return userRepository.save(user);
    }
}
