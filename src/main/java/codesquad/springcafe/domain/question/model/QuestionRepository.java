package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.global.repository.BasicRepository;

import java.util.Collection;

public interface QuestionRepository extends BasicRepository<Question, Long> {
    Collection<Question> findAll();

    void deleteAll();

    void viewCntUp(Long questionId, Question question);
}
