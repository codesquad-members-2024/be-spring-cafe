package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.UserDto;
import codesquad.springcafe.dto.UserUpdateDto;
import codesquad.springcafe.repository.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        List<User> users = userRepository.findAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/create")
    public String createForm() {
        return "user/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute UserDto userDto) {
        userRepository.createUser(userDto);
        return "redirect:/users"; // 이 uri로 리다이렉트
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        User findedUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        model.addAttribute("user", findedUser);
        logger.debug("프로필 조회: {}", findedUser.toDto());
        return "user/profile";
    }

    @GetMapping("/{userId}/update")
    public String updateForm(HttpSession session, @PathVariable("userId") String userId) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null || !loggedInUser.matchUserId(userId)) {
            // 세션에 로그인한 사용자의 ID가 없거나, 요청된 ID와 일치하지 않으면 권한 없음 페이지로 리다이렉트
            return "redirect:/";
        }
        return "user/update";
    }

    @PutMapping("/{userId}/update") // 실패하면 어떻게 되는거지?
    public String update(HttpSession session, @PathVariable("userId") String userId,
                         @ModelAttribute UserUpdateDto userUpdateDto, Model model) {
        User loggedInUser = (User) session.getAttribute("user");
        if (loggedInUser == null || !loggedInUser.matchUserId(userId)) {
            // 세션에 로그인한 사용자의 ID가 없거나, 요청된 ID와 일치하지 않으면 권한 없음 페이지로 리다이렉트
            return "redirect:/";
        }

        if (!loggedInUser.matchPassword(userUpdateDto.getPassword())) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "user/update";
        }

        userRepository.updateUser(loggedInUser.getUserId(), userUpdateDto);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String userId, @RequestParam String password, Model model) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty() || !optionalUser.get().matchPassword(password)) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "user/login";
        }

        User user = optionalUser.get();
        session.setAttribute("user", user);
        logger.debug("로그인 성공: {}", user.toDto());

        String redirectUrl = (String) session.getAttribute("redirectUrl");
        if (redirectUrl != null) {
            session.removeAttribute("redirectUrl");
            return "redirect:" + redirectUrl;
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        logger.debug("로그아웃");
        return "redirect:/";
    }
}