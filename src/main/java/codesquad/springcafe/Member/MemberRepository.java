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
        logger.info("모든 회원 정보 조회");
        return members;
    }

    public Optional<Member> getMember(String userId) {
        logger.info("회원 정보 조회: {}", userId);
        return members.stream()
                .filter(user -> user.getMemberId().equals(userId))
                .findFirst();
    }
}
