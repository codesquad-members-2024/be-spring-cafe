package codesquad.springcafe.Member;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberRepository {
    private static final List<Member> members = new ArrayList<>();

    public static void addMember(Member member) {
        members.add(member);
    }

    public static List<Member> getAllMembers() {
        return members;
    }

    public static Member getMember(String userId) {
        return members.stream()
                .filter(user -> user.getMemberId().equals(userId))
                .findFirst()
                .orElseThrow(null);
    }
}
