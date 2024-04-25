package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.global.repository.BasicRepository;

import java.util.Collection;
import java.util.Optional;

public interface QuestionRepository extends BasicRepository<Question, Long> {

    Optional<Question> findById(Long id, Boolean deleted);

    Collection<Question> findAll();

    void deleteAll();

    void viewCntUp(Long questionId, Question question);
}
