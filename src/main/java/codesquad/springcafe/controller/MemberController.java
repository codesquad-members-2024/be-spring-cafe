package codesquad.springcafe.controller;

import codesquad.springcafe.domain.Member;
import codesquad.springcafe.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/new")
    public String createForm() {
        return "user/form";
    }

    @PostMapping()
    public String create(MemberForm form) {
        memberService.join(form);
        return "redirect:/users/list";
    }

    @GetMapping(value = "/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        members.forEach(System.out::println);
        model.addAttribute("members", members);
        return "user/list";
    }

    @GetMapping(value = "/{userId}")
    public String profile(@PathVariable("userId") Long userId, Model model) {
        // model에 프로필 정보 넣음
        Member member;
        try {
            member = memberService.findOne(userId);
        } catch (Exception e) {
            return "redirect:/users/list";
        }
        model.addAttribute("members", member);
        return "user/profile";
    }

    @GetMapping("/form")
    public String getUserForm() {
        return "user/form";
    }
}
