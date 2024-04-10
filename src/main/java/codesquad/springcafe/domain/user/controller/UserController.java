package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.UserJoinData;
import codesquad.springcafe.domain.user.data.UserListData;
import codesquad.springcafe.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String join(@ModelAttribute UserJoinData userJoinData) {
        userService.join(userJoinData);
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        UserListData userListData = userService.getUsers();

        model.addAttribute("totalUserCnt", userListData.getTotalUserCnt());
        model.addAttribute("users", userListData.getUserList());

        return "user/list";
    }
}
