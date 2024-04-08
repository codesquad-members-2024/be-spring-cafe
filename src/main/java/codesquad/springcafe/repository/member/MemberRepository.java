package codesquad.springcafe.repository.member;

import codesquad.springcafe.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(long id);

    List<Member> findAll();

    void clear();
}
