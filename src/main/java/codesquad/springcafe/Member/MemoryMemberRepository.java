package codesquad.springcafe.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemoryMemberRepository.class);
    private final List<Member> members = new ArrayList<>();

    @Override
    public void addMember(Member member) {
        members.add(member);
        logger.info("회원 추가: {}", member.getMemberId());
    }

    @Override
    public List<Member> getAllMembers() {
        return members;
    }

    @Override
    public Optional<Member> findById(String memberId) {
        return members.stream()
                .filter(member -> member.getMemberId().equals(memberId))
                .findFirst();
    }

    @Override
    public void clear() {
        members.clear();
    }

    @Override
    public void updateMemberInfo(Member updatedMember) {
        findById(updatedMember.getMemberId()).ifPresent(member -> {
            if (member.getPassword().equals(updatedMember.getPassword())) {
                member.setName(updatedMember.getName());
                member.setEmail(updatedMember.getEmail());
                logger.info("회원 정보 업데이트: {}", updatedMember.getMemberId());
            } else {
                logger.warn("비밀번호 불일치: {}", updatedMember.getMemberId());
            }
        });
    }
}
