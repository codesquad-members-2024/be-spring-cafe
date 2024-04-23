package codesquad.springcafe.domain.comment.model;

import codesquad.springcafe.global.repository.BasicRepository;

import java.util.Collection;

public interface CommentRepository extends BasicRepository<Comment, Long> {
    void deleteAll();

    Collection<Comment> findByQuestionId(Long questionId);
}
