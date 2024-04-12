package codesquad.springcafe.Member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @GetMapping("/user/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/user")
    public String register(Member member) {
        MemberRepository.addMember(member);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showList(Model model) {
        model.addAttribute("users",MemberRepository.getAllMembers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String memberId) {
        model.addAttribute("user", MemberRepository.getMember(memberId));
        return "user/profile";
    }
}
