package codesquad.springcafe.controller;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.DB.MemoryUserDatabase;
import codesquad.springcafe.domain.UserUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  
    @GetMapping("/users/success")
    // @RequestParam을 통해 쿼리문의 userId를 받아온다.
    // userId를 통해 DB에서 해당 사용자의 email, nickname을 받아온다.
    public String showLoginSuccess(@RequestParam String userId, Model model) {
        Optional<User> userOptional = MemoryUserDatabase.getUser(userId);
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
        MemoryUserDatabase.saveUser(user);
        logger.debug("new user: " + user.toString());

        // login success 페이지를 위해 쿼리로 userId 전달
        return "redirect:/users/success"+"?userId="+user.getUserId();
    }

    @GetMapping("/users/list")
    public String showUsers(Model model) {
        model.addAttribute("users", MemoryUserDatabase.getAllUsers()); // 전체 user 반환
        model.addAttribute("totalNumber", Integer.toString(MemoryUserDatabase.getUserSize())); //user 개수 반환

        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        Optional<User> userOptional = MemoryUserDatabase.getUser(userId);
        if(userOptional.isEmpty()){
            return "redirect:/main";
        }

        User user = userOptional.get();
        model.addAttribute("user", user); // profile.html에 user 객체 넘겨주기
        return "/user/profile";
    }

    @GetMapping("/users/update/{userId}")
    // 프로필 수정 폼 보여주기
    public String showUpdateForm(@PathVariable String userId, Model model) {
        model.addAttribute("userId", userId);

        return "/user/update_form";
    }

    @PutMapping("/users/update/{userId}")
    // 사용자가 입력한 수정 폼을 받아 프로필 수정하기
    public String updateProfile(@PathVariable String userId, @ModelAttribute UserUpdate userUpdate, Model model) {
        Optional<User> userOptional = MemoryUserDatabase.getUser(userId); // 해당 userId의 데이터를 가져온다
        if(userOptional.isEmpty()){
            return "redirect:/main";
        }
        User user = userOptional.get();

        if(!user.comparePassword(userUpdate.getPassword())){ // 비밀번호가 일치하지 않는다면
            model.addAttribute("isIncorrectPassword", true); // 올바르지 않는 pw
            model.addAttribute("userId", userId);
            return "/user/update_form";
        }

        user.update(userUpdate);
        logger.debug("user update: " + user.toString());
        return "redirect:/users/list";
    }

}
