package codesquad.springcafe.service;

import codesquad.springcafe.controller.MemberForm;
import codesquad.springcafe.domain.Member;
import codesquad.springcafe.domain.Profile;
import codesquad.springcafe.repository.MemberRepository;
import codesquad.springcafe.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public MemberService(
            MemberRepository memberRepository,
            ProfileRepository profileRepository
    ) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    public Long join(MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());
        member.setEmail(memberForm.getEmail());        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member);
        Member savedMember = memberRepository.save(member);

        Profile profile = new Profile(savedMember.getId(), memberForm.getAddress());
        profileRepository.save(profile);

        return savedMember.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Profile findProfileByMemberId(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new IllegalStateException("프로필 없음"));
        return profile;
    }
}
