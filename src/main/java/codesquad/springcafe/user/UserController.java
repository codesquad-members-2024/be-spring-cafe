package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao= userDao;
    }

    @GetMapping("/user/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        // 저장소에서 유저 찾기
        Optional<User> optUser = userDao.findUser(userId);
        // 유저를 View에 넘기기 전 필요한 정보만 DTO에 담기
        Optional<UserDto> optUserDTO = optUser.map(user -> {
            UserDto userDTO = new UserDto();
            userDTO.setName(user.getNickName());
            userDTO.setEmail(user.getEmail());
            return userDTO;
        });

        // userID 를 찾으면 유저 프로필 조회, 못 찾으면 white Lable 페이지 반환
        return optUserDTO.map(userDto -> {
            model.addAttribute("user", userDto);
            return "user/profile";
        }).orElse("error");
    }

    // 회원가입 기능
    @PostMapping("/user")
    public String create(@ModelAttribute UserCreationDto userDTO) {
        final String userId = userDTO.getUserId();
        final String password = userDTO.getPassword();
        final String name = userDTO.getName();
        final String email = userDTO.getEmail();

        User user = new User(userId, password, name, email);
        userDao.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        // 저장소에서 모든 유저 목록 찾기
        Collection<User> users = userDao.getAllUsers();
        // 인덱스 번호를 유저마다 붙여 View 에게 전달하는 DTO생성
        AtomicLong atomicLong = new AtomicLong(0L);
        List<UserDto> userDtos = users.stream()
                .map(user -> {
                    UserDto userDTO = new UserDto();
                    userDTO.setIndex(atomicLong.incrementAndGet());
                    userDTO.setUserId(user.getUserId());
                    userDTO.setName(user.getNickName());
                    userDTO.setEmail(user.getEmail());
                    return userDTO;
                }).toList();

        model.addAttribute("users", userDtos);
        return "user/list";
    }

    @GetMapping("/user/{userId}/form")
    public String showUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/{userId}/form")
    public String updateUser(@PathVariable String userId, UserCreationDto dto) {
        User user = new User(userId, dto.getPassword(), dto.getName(), dto.getEmail());
        userDao.updateUser(user);
        return "redirect:/users";
    }
}
