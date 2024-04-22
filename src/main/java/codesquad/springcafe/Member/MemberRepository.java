package codesquad.springcafe.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberRepository.class);
    private final List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
        logger.info("회원 추가: {}", member.getMemberId());
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> findById(String memberId) {
        return members.stream()
                .filter(member -> member.getMemberId().equals(memberId))
                .findFirst();
    }
    public void clear() {members.clear();}

    public boolean updateMemberInfo(String memberId, Member updatedMember) {
        return findById(memberId).map(member -> {
            if (member.getPassword().equals(updatedMember.getPassword())) {
                member.setName(updatedMember.getName());
                member.setEmail(updatedMember.getEmail());
                logger.info("회원 정보 업데이트: {}", memberId);
                return true;
            } else {
                logger.warn("비밀번호 불일치: {}", memberId);
                return false;
            }
        }).orElseGet(() -> {
            logger.warn("해당 회원을 찾을 수 없음: {}", memberId);
            return false;
        });
    }
}