package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.domain.UpdatedUser;
import codesquad.springcafe.database.user.UserDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserDatabase userDatabase;

    @Autowired
    public UserController(@Qualifier("H2UserDatabase") UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    @GetMapping("/users/success")
    // @RequestParam을 통해 쿼리문의 userId를 받아온다.
    // userId를 통해 DB에서 해당 사용자의 email, nickname을 받아온다.
    public String showLoginSuccess(@RequestParam String userId, Model model) {
        Optional<User> userOptional = userDatabase.getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("email", user.getEmail());
            model.addAttribute("nickname", user.getNickname());
        }

        return "/user/login_success";
    }

    @PostMapping("/user/create")
    // @ModelAttribute 어노테이션 을 통해 Post body를 파싱해 user객체로 반환한다.
    public String saveUser(@ModelAttribute User user) {
        userDatabase.saveUser(user);
        logger.debug("new user: " + user.toString());

        // login success 페이지를 위해 쿼리로 userId 전달
        return "redirect:/users/success"+"?userId="+user.getId();
    }

    @GetMapping("/users/list")
    public String showUsers(Model model) {
        model.addAttribute("users", userDatabase.getAllUsers()); // 전체 user 반환
        model.addAttribute("totalNumber", Integer.toString(userDatabase.getUsersSize())); //user 개수 반환

        return "/user/list";
    }

    @GetMapping("/users/{id}")
    public String showProfile(@PathVariable String id, Model model) {
        Optional<User> userOptional = userDatabase.getUserById(id);
        if(userOptional.isEmpty()){
            return "redirect:/main";
        }

        User user = userOptional.get();
        model.addAttribute("user", user); // profile.html에 user 객체 넘겨주기
        return "/user/profile";
    }

    @GetMapping("/users/update/{id}")
    // 프로필 수정 폼 보여주기
    public String showUpdateForm(@PathVariable String id, Model model) {
        model.addAttribute("id", id);

        return "/user/update_form";
    }

    @PutMapping("/users/update/{id}")
    // 사용자가 입력한 수정 폼을 받아 프로필 수정하기
    public String updateProfile(@PathVariable String id, @ModelAttribute UpdatedUser updatedUser, Model model) {
        Optional<User> userOptional = userDatabase.getUserById(id); // 해당 id 데이터를 가져온다
        if(userOptional.isEmpty()){
            return "redirect:/main";
        }
        User user = userOptional.get();

        if(!user.comparePassword(updatedUser.getPassword())){ // 비밀번호가 일치하지 않는다면
            model.addAttribute("isIncorrectPassword", true); // 올바르지 않는 pw
            model.addAttribute("id", id);
            return "/user/update_form";
        }

        userDatabase.updateUser(id, updatedUser);
        logger.debug("user update: " + user.toString());
        return "redirect:/users/list";
    }

}
