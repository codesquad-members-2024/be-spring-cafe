package codesquad.springcafe.domain.comment.service;

import codesquad.springcafe.domain.article.dto.Article;
import codesquad.springcafe.domain.comment.dto.Comment;
import codesquad.springcafe.domain.comment.dto.DeleteComment;
import codesquad.springcafe.domain.comment.dto.UpdateComment;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.dto.UserIdentity;
import codesquad.springcafe.exceptions.NoSuchCommentException;
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

    @Override
    public Comment getCommentById(String identifier) throws NoSuchCommentException {
        return commentRepository.get(identifier);
    }

    @Override
    public String updateComment(UpdateComment comment) throws NoSuchCommentException {
        commentRepository.update(comment.getIdentifier(), comment.getContents());

        return commentRepository.get(comment.getIdentifier()).getWrittenArticle();
    }

    @Override
    public void deleteComment(DeleteComment comment) throws NoSuchCommentException {
        commentRepository.delete(comment.getIdentifier());
    }

    @Override
    public void addLike(String commentId) throws NoSuchCommentException {
        commentRepository.addLike(commentId);
    }

    @Override
    public boolean userHasPermission(UserIdentity userIdentity, String commentId) throws NoSuchCommentException{
        Comment comment = commentRepository.get(commentId);

        return userIdentity.getName().contentEquals(comment.getWriter());
    }
}
