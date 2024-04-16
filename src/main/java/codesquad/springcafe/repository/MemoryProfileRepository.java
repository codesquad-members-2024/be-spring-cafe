package codesquad.springcafe.repository;

import codesquad.springcafe.domain.Member;
import codesquad.springcafe.domain.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryProfileRepository implements ProfileRepository {

    private static Map<Long, Profile> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Long save(Profile profile) {
        profile.setId(++sequence);
        store.put(profile.getId(), profile);
        return profile.getId();
    }

    @Override
    public Optional<Profile> findByMemberId(Long memberId) {
        return store.values().stream()
                .filter(profile -> profile.getMemberId().equals(memberId))
                .findAny();
    }
}
