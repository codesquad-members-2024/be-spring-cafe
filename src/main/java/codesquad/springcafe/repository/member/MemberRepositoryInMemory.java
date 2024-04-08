package codesquad.springcafe.repository.member;

import codesquad.springcafe.domain.member.Member;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryInMemory implements MemberRepository {
    private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryInMemory.class);
    private final Map<Long, Member> store = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong();

    @Override
    public Member save(Member member) {
        long createdId = sequence.incrementAndGet();
        member.setId(createdId);
        store.put(createdId, member);
        logger.info("saved member={}", member.getId());

        return member;
    }

    @Override
    public Optional<Member> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public void clear() {
        store.clear();
        sequence.set(0L);
    }
}

