package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Member;
import codesquad.springcafe.domain.Profile;
import codesquad.springcafe.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@Controller
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/new")
    public String createForm() {
        return "user/form";
    }

    @PostMapping(value = "/new")
    public String create(MemberForm form) {
        Long joinId = memberService.join(form);
        logger.info("회원가입 성공 {}", joinId);
        return "redirect:/users/list";
    }

    @GetMapping(value = "/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "user/list";
    }

    @GetMapping(value = "/{userId}")
    public String profile(@PathVariable("userId") Long userId, Model model) {
        // model에 프로필 정보 넣음
        Optional<Member> member = memberService.findOne(userId);
        model.addAttribute("member", member.get());
        Profile profileByMemberId = memberService.findProfileByMemberId(userId);
        model.addAttribute("profile", profileByMemberId);
        return "user/profile";
    }
}
