package codesquad.springcafe.domain.question.model;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 기본 repository 로직을 담고 있는 BasicRepository를 상속하는 QuestionRepository를 구현합니다.
 * <p>QuestionRepository에는 BasicRepository에 없는 추가 로직 메서드를 정의할 수 있습니다.
 * <p>DB가 아닌 Map에 질문 게시글 정보를 저장합니다.
 */
@Repository
public class QuestionRepositoryLocal implements QuestionRepository{
    private static final Map<Long, Question> questions = new HashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0);

    public Question save(Question question){
        long questionId = sequence.incrementAndGet();
        question.setId(questionId);
        questions.put(questionId, question);
        return question;
    }

    public Optional<Question> findById(Long id) {
        return Optional.ofNullable(questions.get(id));
    }

    public Collection<Question> findAll() {
        return questions.values();
    }

    public void deleteAll() {
        questions.clear();
    }

    @Override
    public void update(Long questionId, Question updateQuestion) {
        questions.put(questionId, updateQuestion);
    }
}
