package codesquad.springcafe.comment;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleDao;
import codesquad.springcafe.comment.dto.CommentCreationRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    private ArticleDao articleDao;
    private CommentDao commentDao;

    public CommentService(ArticleDao articleDao, CommentDao commentDao) {
        this.articleDao = articleDao;
        this.commentDao = commentDao;
    }

    public void save(int articleId, CommentCreationRequestDto dto) {
        Optional<Article> optArticle = articleDao.findBy(articleId);
        String writer = optArticle.get().getWriter();

        Comment comment = new Comment(articleId, writer, dto.getContents(), LocalDateTime.now());
        commentDao.save(comment);
    }
}
