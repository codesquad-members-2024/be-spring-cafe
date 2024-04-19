package codesquad.springcafe.user;

import codesquad.springcafe.user.dto.UserCreationDto;
import codesquad.springcafe.user.dto.UserViewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        UserViewDto userProfile = service.profile(userId);
        model.addAttribute("user", userProfile);
        return "user/profile";
    }

    // 회원가입 기능
    @PostMapping("/user")
    public String create(@ModelAttribute UserCreationDto userCreationDto) {
        service.save(userCreationDto);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        // 저장소에서 모든 유저 목록 찾기
        List<UserViewDto> userDtos = service.getAllUsers();
        // 인덱스 번호를 유저마다 붙여 View 에게 전달하는 DTO생성
        AtomicLong atomicLong = new AtomicLong(0L);
        userDtos.forEach(dto -> dto.numbering(atomicLong.incrementAndGet()));

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
        service.update(userId, dto);
        return "redirect:/users";
    }

//    @GetMapping("/user/login")
//    public String login(@ModelAttribute UserLoginRequestDto dto) {
//        return service.login(dto);
//    }
}
