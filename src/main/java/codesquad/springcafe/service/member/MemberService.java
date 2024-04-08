package codesquad.springcafe.service.member;

import codesquad.springcafe.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member join(Member member);

    Optional<Member> findMember(long id);

    List<Member> findAllMember();
}
