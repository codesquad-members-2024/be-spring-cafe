package codesquad.springcafe.controller.member;

import codesquad.springcafe.controller.SessionConst;
import codesquad.springcafe.domain.member.Member;
import codesquad.springcafe.service.exception.ResourceNotFoundException;
import codesquad.springcafe.service.exception.UnauthorizedException;
import codesquad.springcafe.service.member.MemberService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String members(Model model) {
        List<Member> members = memberService.findAllMember();
        model.addAttribute("members", members);

        return "members/list";
    }

    @GetMapping("/add")
    public String form(@ModelAttribute("member") Member member) {
        return "members/form";
    }

    @PostMapping("/add")
    public String join(@Validated @ModelAttribute("member") Member member, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/form";
        }

        memberService.join(member);

        return "redirect:/members";
    }

    @GetMapping("/{loginId}")
    public String memberInfo(@PathVariable("loginId") String loginId, Model model) {
        /* 멤버 존재 검증 */
        Member findMember = validateExists(loginId);

        /* Member 객체를 DTO로 변환 */
        ResponseMember responseMember = convertToDTO(findMember);
        model.addAttribute("member", responseMember);

        return "members/profile";
    }

    private ResponseMember convertToDTO(Member member) {
        return new ResponseMember(member.getLoginId(), member.getUserName(), member.getEmail());
    }

    @GetMapping("/{loginId}/update")
    public String updateForm(@SessionAttribute(name = SessionConst.SESSION_ID, required = false) String sessionMemberId,
                             @PathVariable("loginId") String loginId, @ModelAttribute("form") UpdateMember form) {
        /* 로그인 검증 */
        validateLoginId(sessionMemberId, loginId);

        /* 멤버 존재 검증 */
        Member findMember = validateExists(loginId);

        fillForm(form, findMember);

        return "members/updateForm";
    }

    private void validateLoginId(String sessionMemberId, String loginId) {
        if (sessionMemberId == null || !sessionMemberId.equals(loginId)) {
            throw new UnauthorizedException("다른 회원의 정보를 수정할 수 없습니다. 로그인 멤버 아이디: " + loginId);
        }
    }

    private Member validateExists(String loginId) {
        Optional<Member> findMember = memberService.findMember(loginId);
        if (findMember.isEmpty()) {
            throw new ResourceNotFoundException("멤버를 찾을 수 없습니다. 멤버 아이디: " + loginId);
        }
        return findMember.get();
    }

    private void fillForm(UpdateMember form, Member member) {
        form.setLoginId(member.getLoginId());
        form.setUserName(member.getUserName());
        form.setEmail(member.getEmail());
    }

    @PutMapping("/{loginId}")
    public String update(@PathVariable("loginId") String loginId,
                         @Validated @ModelAttribute("form") UpdateMember form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "members/updateForm";
        }

        Optional<Member> optionalMember = memberService.findMember(loginId);

        /* 존재하지 않는 회원일 때 */
        if (optionalMember.isEmpty()) {
            bindingResult.addError(new ObjectError("form", "존재하지 않는 회원입니다."));
            return "members/updateForm";
        }

        /* 기존 비밀번호와 일치하지 않을 때 */
        Member findMember = optionalMember.get();
        if (!memberService.validatePassword(findMember, form.getBeforePassword())) {
            bindingResult.addError(new FieldError("form", "beforePassword", "기존 비밀번호를 잘못 입력하셨습니다. 다시 확인해주세요."));
            return "members/updateForm";
        }

        /* 정상 로직 */
        memberService.update(loginId, form);
        return "redirect:/members/{loginId}";
    }
}
