package codesquad.springcafe.Member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private final List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        members.add(member);
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> getMember(String userId) {
        return members.stream()
                .filter(user -> user.getMemberId().equals(userId))
                .findFirst();
    }
}
