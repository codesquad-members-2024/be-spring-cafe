package codesquad.springcafe.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void addMember(Member member);
    Optional<Member> findById(String memberId);
    List<Member> getAllMembers();
    void updateMemberInfo(Member member);
    void clear();
}
