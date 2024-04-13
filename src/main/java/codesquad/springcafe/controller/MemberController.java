package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Member;
import codesquad.springcafe.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
}
