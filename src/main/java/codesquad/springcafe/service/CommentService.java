package codesquad.springcafe.service;

import codesquad.springcafe.database.comment.CommentDatabase;
import codesquad.springcafe.exception.CommentNotFoundException;
import codesquad.springcafe.form.comment.CommentWriteForm;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Comment;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public static final int commentsPerPage = 15;
    private final Logger logger = LoggerFactory.getLogger(CommentService.class);
    private final CommentDatabase commentDatabase;

    public CommentService(CommentDatabase commentDatabase) {
        this.commentDatabase = commentDatabase;
    }

    public Comment writeComment(Long articleId, String writer, CommentWriteForm commentWriteForm) {
        Comment comment = new Comment(writer, commentWriteForm.getContent(), articleId,
                LocalDateTime.now());
        commentDatabase.add(comment);
        logger.info("새로운 코멘트가 추가되었습니다. {}", comment);
        return comment;
    }

    public List<Comment> getComments(Long articleId) {
        return commentDatabase.findAll(articleId);
    }

    public List<Comment> getCommentsByOffset(Long articleId, Long offset) {
        return commentDatabase.findPageComments(articleId, offset, commentsPerPage);
    }

    public void deleteComments(Long articleId) {
        commentDatabase.findAll(articleId)
                .forEach(comment -> deleteComment(comment.getId()));
    }

    public Comment deleteComment(Long id) {
        Comment comment = getComment(id);
        commentDatabase.softDelete(id);
        comment.delete();
        logger.info("코멘트가 삭제되었습니다. {}", comment);
        return comment;
    }

    public Long getCommentCount(Article article) {
        return commentDatabase.count(article.getId());
    }

    public Map<Long, Long> getCommentCounts(List<Article> articles) {
        return articles.stream()
                .map(Article::getId)
                .collect(Collectors.toMap(Function.identity(), commentDatabase::count));
    }

    public Comment getComment(Long id) {
        return commentDatabase.findBy(id).orElseThrow(() -> new CommentNotFoundException(id));
    }
}
