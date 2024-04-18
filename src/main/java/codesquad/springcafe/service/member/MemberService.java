package codesquad.springcafe.service.member;

import codesquad.springcafe.controller.member.UpdateMember;
import codesquad.springcafe.domain.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member join(Member member);

    Optional<Member> findMember(String loginId);

    List<Member> findAllMember();

    boolean validatePassword(Member findMember, String beforePassword);

    void update(String loginId, UpdateMember updateParam);

    Optional<Member> login(String loginId, String password);
}
