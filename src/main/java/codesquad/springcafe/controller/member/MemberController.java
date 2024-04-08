package codesquad.springcafe.controller.member;

import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.service.member.MemberService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("")
    public String members(Model model) {
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members", members);

        return "members/list";
    }

    @GetMapping("/add")
    public String form(@ModelAttribute("form") MemberForm form) {
        return "members/form";
    }

    @PostMapping("/add")
    public String join(@ModelAttribute("form") MemberForm form) {
        Member member = createMember(form);

        memberService.join(member);

        return "redirect:/members";
    }

    @GetMapping("/{id}")
    public String memberInfo(@PathVariable("id") long id, Model model) {
        Optional<Member> findMember = memberService.findMember(id);

        if (findMember.isEmpty()) {
            return "error/404";
        }

        /* Member 객체를 DTO로 변환 */
        Member member = findMember.get();
        ResponseMember responseMember = convertToDTO(member);
        model.addAttribute("member", responseMember);

        return "members/profile";
    }

    private Member createMember(MemberForm form) {
        String loginId = form.getLoginId();
        String password = form.getPassword();
        String userName = form.getUserName();
        String email = form.getEmail();

        return new Member(loginId, password, userName, email);
    }

    private ResponseMember convertToDTO(Member member) {

        return new ResponseMember(member.getLoginId(), member.getUserName(), member.getEmail());
    }
}
