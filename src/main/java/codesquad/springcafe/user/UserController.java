package codesquad.springcafe.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserDao userDao;
    private final UserMapper userMapper;

    public UserController(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @GetMapping("/user/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        UserViewDto userDto = userDao.findUser(userId)
                .map(userMapper::toProfileDto)
                .orElseThrow(() -> new IllegalArgumentException(userId + "를 찾을 수 없습니다"));

        model.addAttribute("users", userDto);
        return "user/profile";
    }

    // 회원가입 기능
    @PostMapping("/user")
    public String create(@ModelAttribute UserCreationDto userCreationDto) {
        User user = userMapper.toCreateUser(userCreationDto);
        userDao.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        // 저장소에서 모든 유저 목록 찾기
        Collection<User> users = userDao.getAllUsers();
        // 인덱스 번호를 유저마다 붙여 View 에게 전달하는 DTO생성
        AtomicLong atomicLong = new AtomicLong(0L);
        List<UserViewDto> userDtos = users.stream()
                .map(user -> userMapper.toListDto(user, atomicLong.incrementAndGet()))
                .toList();

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
        User user = userMapper.toUpdateUser(userId, dto);
        userDao.updateUser(user);
        return "redirect:/users";
    }
}
