package codesquad.springcafe.controller;


import codesquad.UserProfileData;
import codesquad.springcafe.model.User;
import codesquad.springcafe.repository.user.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/add")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("/user/add")
    public String create(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String userList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute(users);
        return "user/list";
    }

    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserProfileData dto = new UserProfileData();
            dto.setUserId(user.getUserId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            model.addAttribute(dto);
            return "user/profile";
        }
        //todo 해당 아이디의 user 없을 시 예외 처리
        // 누군가 url을 임의로 입력 했을 경우이다
        return null;
    }
}