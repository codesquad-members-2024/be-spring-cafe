package codesquad.springcafe.controller;

import codesquad.springcafe.dto.User;
import codesquad.springcafe.service.UserService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserMatchController {
    private static final Logger logger = LoggerFactory.getLogger(UserMatchController.class);
    private final UserService userService;

    @Autowired
    public UserMatchController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/match_pw/{userId}")
    public Map<String, Object> matchPw(@PathVariable String userId, @RequestParam String userPw) throws Exception {
        User user = userService.findUserByUserId(userId);
        Map<String, Object> response = new HashMap<>();
        if (user.matchPw(userPw)) {
            response.put("valid", true);
        } else {
            response.put("valid", false);
        }
        return response;
    }
}
