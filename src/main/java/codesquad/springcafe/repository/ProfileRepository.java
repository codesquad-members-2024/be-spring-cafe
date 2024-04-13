package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Member;
import codesquad.springcafe.domain.Profile;

import java.util.Optional;

public interface ProfileRepository {
    Long save(Profile profile);
    Optional<Profile> findByMemberId(Long memberId);

}
