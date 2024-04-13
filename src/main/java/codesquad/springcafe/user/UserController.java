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

    @GetMapping("/user/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        // 저장소에서 유저 찾기
        Optional<User> optUser = userRepository.findUser(userId);
        // 유저를 View에 넘기기 전 필요한 정보만 DTO에 담기
        Optional<UserDTO> optUserDTO = optUser.map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            return userDTO;
        });

        // userID 를 찾으면 유저 프로필 조회, 못 찾으면 로그인 페이지로 이동.
        return optUserDTO.map(userDTO -> {
            model.addAttribute("user", userDTO);
            return "user/profile";
        }).orElse("user/login");
    }

    @GetMapping("/user/{userId}/form")
    public String updateUser(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/updateForm";
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
        // 저장소에서 모든 유저 목록 찾기
        Collection<User> users = userRepository.getAllUsers();
        // 인덱스 번호를 유저마다 붙여 View 에게 전달하는 DTO생성
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
