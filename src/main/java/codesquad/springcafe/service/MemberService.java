package codesquad.springcafe.service;

import codesquad.springcafe.controller.MemberForm;
import codesquad.springcafe.domain.Member;
import codesquad.springcafe.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;

    public MemberService(
            MemberRepository memberRepository

    ) {
        this.memberRepository = memberRepository;
    }

    public Long join(MemberForm memberForm) {
        Member member = new Member(memberForm.getLoginId(),
                                    memberForm.getPassword(),
                                    memberForm.getName(),
                                    memberForm.getEmail(),
                                    memberForm.getAddress());
        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);

        logger.debug("회원가입 성공 {}", member.getId());

        return savedMember.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.orElseThrow();
    }

}
