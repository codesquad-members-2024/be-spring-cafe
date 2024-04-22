package codesquad.springcafe.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MemberController {
    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/user/add")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/user/add")
    public String register(@ModelAttribute Member member) {
        memberRepository.addMember(member);
        logger.info("새로운 회원 등록: {}", member.getMemberId());
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String showList(Model model) {
        List<Member> members = memberRepository.getAllMembers();
        model.addAttribute("users", members);
        logger.info("회원 목록 조회");
        return "user/list";
    }

    @GetMapping("/users/{memberId}")
    public String showProfile(Model model, @PathVariable("memberId") String memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            model.addAttribute("user", optionalMember.get());
            logger.info("회원 프로필 조회: {}", memberId);
        } else {
            logger.warn("회원 프로필 조회 실패: {}", memberId);
        }
        return "user/profile";

    }
    @GetMapping("/users/{memberId}/form")
    public String updateForm(@PathVariable String memberId, Model model) {
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(m -> model.addAttribute("member", m));
        return member.isPresent() ? "user/updateForm" : "redirect:/user/list";
    }
    @PutMapping("/user/update/{memberId}")
    public String updateMember(@PathVariable String memberId, @ModelAttribute Member updatedMember) {
        if (memberRepository.updateMemberInfo(memberId, updatedMember)) {
            logger.info("회원 정보 수정 성공: {}", memberId);
        } else {
            logger.warn("회원 정보 수정 실패: {}", memberId);
        }
        return "redirect:/user/list";
    }
}
