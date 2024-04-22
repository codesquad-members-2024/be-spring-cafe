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

    public Optional<Member> getMember(String memberId) {
        return members.stream()
                .filter(user -> user.getMemberId().equals(memberId))
                .findFirst();
    }
    public boolean updateMemberInfo(String memberId, String currentPassword, String newName, String newEmail, String newPassword) {
        Optional<Member> memberOptional = getMember(memberId);
        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (member.getPassword().equals(currentPassword)) {
                member.setName(newName);
                member.setEmail(newEmail);
                member.setPassword(newPassword);
                logger.info("회원 정보 업데이트: {}", memberId);
                return true;
            } else {
                logger.warn("비밀번호 불일치: {}", memberId);
                return false;
            }
        } else {
            logger.warn("해당 회원을 찾을 수 없음: {}", memberId);
            return false;
        }
    }
}
