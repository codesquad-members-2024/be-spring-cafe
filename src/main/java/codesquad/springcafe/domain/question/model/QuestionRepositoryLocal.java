package codesquad.springcafe.domain.question.model;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class QuestionRepositoryLocal implements QuestionRepository{
    private static final Map<Long, Question> questions = new HashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    public Question save(Question question){
        long userId = sequence.incrementAndGet();
        question.setId(userId);
        questions.put(userId, question);
        return question;
    }

    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(questions.get(id));
    }

    public Collection<Question> findAll() {
        return questions.values();
    }

    public Long countAll() {
        return sequence.get();
    }

    public void deleteAll() {
        questions.clear();
    }
}
