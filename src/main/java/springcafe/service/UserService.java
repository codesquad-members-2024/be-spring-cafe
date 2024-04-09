package springcafe.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springcafe.dto.UserDto;
import springcafe.model.User;
import springcafe.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(String id, String password, String username, String email){


        User user = new User(id, password, username, email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.add(user);

        return user;
    }

    public User findById(String id){
        return userRepository.findById(id);
    }

    public List<UserDto> findAll(){
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList){
            userDtoList.add(UserDto.toUserDto(user));
        }

        return userDtoList;

    }
}
