package codesquad.springcafe.domain.question.model;

import codesquad.springcafe.global.repository.BasicRepository;

public interface QuestionRepository extends BasicRepository<Question, Long> {
    void update(Long questionId, Question updateQuestion);

    void viewCntUp(Long questionId, Question question);
}
