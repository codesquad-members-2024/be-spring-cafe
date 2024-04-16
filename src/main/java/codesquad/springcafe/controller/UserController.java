package codesquad.springcafe.controller;

import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.form.user.UserAddForm;
import codesquad.springcafe.form.user.UserEditForm;
import codesquad.springcafe.model.User;
import codesquad.springcafe.util.LoginUserProvider;
import jakarta.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public String userForm(Model model) {
        UserAddForm userAddForm = new UserAddForm("", "", "");
        model.addAttribute("userAddForm", userAddForm);
        return "user/form";
    }

    /**
     * 사용자가 작성한 내용을 바탕으로 유저를 생성하고 데이터베이스에 저장합니다.
     */
    @PostMapping("/add")
    public String addUser(@Validated @ModelAttribute UserAddForm userAddForm, BindingResult bindingResult) {
        validateAddForm(userAddForm, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/form";
        }

        User user = new User(userAddForm.getEmail(), userAddForm.getNickname(), userAddForm.getPassword());
        userDatabase.add(user);
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
        Optional<User> optionalUser = userDatabase.findByNickname(nickname);
        if (optionalUser.isEmpty()) {
            return "redirect:/users";
        }
        model.addAttribute("user", optionalUser.get());
        return "user/profile";
    }

    /**
     * 유저 변경 폼을 사용자에게 보여줍니다.
     *
     * @param nickname 사용자가 수정을 원하는 유저의 닉네임입니다.
     * @param model    데이터 베이스에 찾은 유저를 담습니다.
     * @return 데이터베이스에 일치하는 유저가 없으면 유저 리스트 화면으로 리다이렉트하고, 아니라면 업데이트 폼 화면을 보여줍니다.
     */
    @GetMapping("/edit/{nickname}")
    public String updateForm(@PathVariable String nickname, Model model) {
        Optional<User> optionalUser = userDatabase.findByNickname(nickname);
        if (optionalUser.isEmpty()) {
            return "redirect:/users";
        }
        User user = optionalUser.get();
        String email = user.getEmail();
        UserEditForm userEditForm = new UserEditForm(email, nickname, "", "");
        model.addAttribute("userEditForm", userEditForm);
        return "user/update";
    }

    /**
     * 유저 수정폼에 입력된 정보로 유저 정보를 업데이트합니다.
     *
     * @param nickname     사용자가 수정을 원하는 유저의 닉네임입니다.
     * @param userEditForm 수정 정보가 담긴 폼 정보입니다.
     * @return 유저가 존재하지 않으면 유저 리스트로 이동합니다. 현재 비밀번호가 일치하지 않으면 유저 수정 폼을 다시 보여줍니다. 수정이 정상적으로 완료되면 프로필을 보여줍니다.
     */
    @PutMapping("/edit/{nickname}")
    public String updateUser(@PathVariable String nickname, @Validated @ModelAttribute UserEditForm userEditForm,
                             BindingResult bindingResult, HttpSession session) {
        Optional<User> optionalUser = userDatabase.findByNickname(nickname);
        if (optionalUser.isEmpty()) {
            return "redirect:/users";
        }
        User targetUser = optionalUser.get();

        validateUpdateForm(userEditForm, bindingResult, targetUser);
        if (bindingResult.hasErrors()) {
            logger.error("errors={}", bindingResult);
            return "user/update";
        }

        User updateUser = targetUser.update(userEditForm.getNickname(), userEditForm.getNewPassword());
        userDatabase.update(updateUser);

        session.setAttribute(LoginUserProvider.LOGIN_SESSION_NAME, updateUser);

        logger.info("유저정보가 업데이트 되었습니다. {}", updateUser);
        String newNickname = updateUser.getNickname(); // 유저 닉네임이 수정될 경우를 반영
        return "redirect:/users/profile/" + URLEncoder.encode(newNickname, StandardCharsets.UTF_8);
    }

    private void validateAddForm(UserAddForm userAddForm, BindingResult bindingResult) {
        if (isPresentNickname(userAddForm.getNickname())) {
            bindingResult.rejectValue("nickname", "Duplicate");
        }

        if (isPresentEmail(userAddForm.getEmail())) {
            bindingResult.rejectValue("email", "Duplicate");
        }
    }

    private void validateUpdateForm(UserEditForm userEditForm, BindingResult bindingResult, User user) {
        if (isPresentNickname(userEditForm.getNickname()) && !user.hasSameNickname(userEditForm.getNickname())) {
            bindingResult.rejectValue("nickname", "Duplicate");
        }

        if (!user.hasSamePassword(userEditForm.getCurrentPassword())) {
            bindingResult.rejectValue("currentPassword", "Wrong");
        }
    }

    private boolean isPresentNickname(String nickname) {
        return userDatabase.findByNickname(nickname).isPresent();
    }

    private boolean isPresentEmail(String email) {
        return userDatabase.findByEmail(email).isPresent();
    }
}
