package codesquad.springcafe.Member;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String addMember(Member member){
        memberRepository.addMember(member);
        return member.getMemberId();
    }

    public List<Member> findAllMembers(){
        return memberRepository.getAllMembers();
    }

    public Optional<Member> findMember(String memberId){
        return memberRepository.findById(memberId);
    }
    public boolean updateMemberInfo(String memberId, Member updatedMember){
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        if (memberOptional.isPresent()) {
            Member memberToUpdate = memberOptional.get();
            memberToUpdate.setName(updatedMember.getName());
            memberToUpdate.setEmail(updatedMember.getEmail());
            memberToUpdate.setPassword(updatedMember.getPassword());

            memberRepository.updateMemberInfo(memberToUpdate);
            return true;
        } else {
            return false;
        }
    }
}
