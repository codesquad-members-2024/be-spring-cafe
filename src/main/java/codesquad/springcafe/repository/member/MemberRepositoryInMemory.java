package codesquad.springcafe.repository.member;

import codesquad.springcafe.controller.member.UpdateMember;
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
    public Optional<Member> findById(String loginId) {
        return store.values().stream()
                .filter(member -> member.getLoginId().equals(loginId))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return store.values().stream().toList();
    }

    @Override
    public void update(String loginId, UpdateMember updateParam) {
        Optional<Member> optionalMember = findById(loginId);
        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        Member findMember = optionalMember.get();
        findMember.setUserName(updateParam.getUserName());
        findMember.setPassword(updateParam.getAfterPassword());
        findMember.setEmail(updateParam.getEmail());
    }

    @Override
    public void clear() {
        store.clear();
        sequence.set(0L);
    }
}

