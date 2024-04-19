package codesquad.springcafe.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {
    private
    final MemberRepository memberRepository;
    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/user/add")
    public String createform(){
        return "user/form";
    }

    @PostMapping("/user/add")
    public String register(@ModelAttribute Member member) {
        memberRepository.addMember(member);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showList(Model model) {
        List<Member> members = memberRepository.getAllMembers();
        model.addAttribute("users", members);
        return "user/list";
    }
    
    @GetMapping("/user/profile/{userId}")
    public String showProfile(Model model, @PathVariable("userId") String memberId) {
        Optional<Member> optionalMember = memberRepository.getMember(memberId);
        model.addAttribute("user", optionalMember.get());
        return "user/profile";
    }
}
