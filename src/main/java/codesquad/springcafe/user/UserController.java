package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // userID 를 찾으면 유저 프로필 조회, 못 찾으면 로그인 페이지로 이동.
    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        Optional<User> optUser = userRepository.findUser(userId);
        Optional<UserDTO> optUserDTO = optUser.map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            return userDTO;
        });

        return optUserDTO.map(userDTO -> {
            model.addAttribute("user", userDTO);
            return "user/profile";
        }).orElse("user/login");
    }

    // 회원가입 기능
    @PostMapping("/user")
    public String create(@ModelAttribute UserCreationDTO userDTO) {
        final String userId = userDTO.getUserId();
        final String password = userDTO.getPassword();
        final String name = userDTO.getName();
        final String email = userDTO.getEmail();
        User user = new User(userId, password, name, email);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        Collection<User> users = userRepository.getAllUsers();
        AtomicLong atomicLong = new AtomicLong(0L);
        List<UserDTO> userDTOs = users.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setIndex(atomicLong.incrementAndGet());
                    userDTO.setUserId(user.getUserId());
                    userDTO.setName(user.getName());
                    userDTO.setEmail(user.getEmail());
                    return userDTO;
                }).toList();

        model.addAttribute("users", userDTOs);
        return "user/list";
    }

}
