package codesquad.springcafe.user;

import codesquad.springcafe.user.dto.UserCreationDto;
import codesquad.springcafe.user.dto.UserInfoEditRequestDto;
import codesquad.springcafe.user.dto.UserViewDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public String create(@Valid @ModelAttribute UserCreationDto userCreationDto) {
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
    public String showUpdateForm(@PathVariable String userId, Model model, HttpSession session) {
        // 세션이 없는 사용자가 접근한 경우 에러
        String value = (String) session.getAttribute("sessionUserId");
        if (!userId.equals(value)) {
            return "../static/user/updateFailed";
        }
        model.addAttribute("userId", userId);
        return "user/updateForm";
    }

    @PutMapping("/user/{userId}/form")
    public String updateUser(@PathVariable String userId, @Valid @ModelAttribute UserInfoEditRequestDto dto,
                             BindingResult bindingResult) {
        // 개인정보 수정 폼에 이상한 입력값이 들어오면 에러
        if (bindingResult.hasErrors()) {
            return "../static/user/updateFailed";
        }
        log.debug("유저 편집 정보 : {}", dto.toString());
        service.updateUser(userId, dto);
        return "redirect:/users";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam String userId, @RequestParam String password, HttpSession session) {
        Optional<UserViewDto> optUser = service.doLogin(userId, password);

        return optUser.map(user -> {
                    log.debug("{} 에 대해 세션을 추가합니다", user.getUserId());
                    session.setAttribute("sessionUserId", user.getUserId());
                    return "redirect:/";
                })
                .orElseThrow(() -> new IllegalArgumentException(userId + "는 로그인 할 수 없습니다"));
    }
}
