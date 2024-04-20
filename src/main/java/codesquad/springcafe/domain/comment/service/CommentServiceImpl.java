package codesquad.springcafe.domain.comment.service;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.add(comment);
    }

    @Override
    public List<Comment> getComments(Article article) {
        return commentRepository.getComments(article.getIdentifier());
    }
}
