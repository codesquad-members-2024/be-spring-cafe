package springcafe.user.service;

import org.springframework.stereotype.Service;
import springcafe.user.dto.UserDto;
import springcafe.user.model.User;
import springcafe.user.repository.UserRepository;

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
