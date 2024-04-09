package codesquad.springcafe.controller;

import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.model.User;
import jakarta.annotation.PostConstruct;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserDatabase userDatabase;

    public UserController(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    /**
     * 유저 리스트 전체를 조회할 수 있습니다.
     */
    @GetMapping
    public String userList(Model model) {
        List<User> users = userDatabase.findAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    /**
     * 회원가입 폼을 사용자에게 보여줍니다.
     */
    @GetMapping("/add")
    public String userForm() {
        return "user/form";
    }

    /**
     * 사용자가 작성한 내용을 바탕으로 유저를 생성하고 데이터베이스에 저장합니다.
     */
    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userDatabase.save(user);
        logger.info("새로운 유저가 생성되었습니다. {}", user);
        return "redirect:/users";
    }

    /**
     * 사용자가 요청한 닉네임으로 데이터베이스에 저장된 프로필을 조회합니다.
     *
     * @param nickname 사용자가 요청한 닉네임입니다.
     * @return 닉네임과 일치하는 유저가 없다면 홈으로 리다이렉트합니다. 아니면 유저 프로필 경로를 반환합니다.
     */
    @GetMapping("/profile/{nickname}")
    public String userProfile(@PathVariable String nickname, Model model) {
        Optional<User> optionalUser = userDatabase.findBy(nickname);
        if (optionalUser.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("user", optionalUser.get());
        return "user/profile";
    }

    @PostConstruct
    public void createTestUser() {
        User user = new User("sangchu@gmail.com", "상추", "123");
        userDatabase.save(user);
    }
}
