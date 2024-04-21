package codesquad.springcafe.service;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.comment.CommentDatabase;
import codesquad.springcafe.exception.ArticleHasCommentsException;
import codesquad.springcafe.exception.ArticleNotFoundException;
import codesquad.springcafe.form.article.ArticleWriteForm;
import codesquad.springcafe.model.Article;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    private final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleDatabase articleDatabase;
    private final CommentDatabase commentDatabase;

    public ArticleService(ArticleDatabase articleDatabase, CommentDatabase commentDatabase) {
        this.articleDatabase = articleDatabase;
        this.commentDatabase = commentDatabase;
    }

    public Article write(ArticleWriteForm articleWriteForm, String nickname) {
        Article article = new Article(nickname, articleWriteForm.getTitle(),
                articleWriteForm.getContent(), LocalDateTime.now());
        articleDatabase.add(article);
        logger.info("새로운 게시물이 추가되었습니다. {}", article);
        return article;
    }

    public Set<Long> getArticleIds(String writer) {
        List<Article> articles = articleDatabase.findAll(writer);
        return articles.stream()
                .map(Article::getId)
                .collect(Collectors.toSet());
    }

    public Article viewArticle(Long id) {
        Article article = getArticle(id);
        articleDatabase.increaseViews(id);
        article.increaseViews();
        return article;
    }


    public Article update(Long id, ArticleWriteForm articleWriteForm) {
        Article article = getArticle(id);

        String newTitle = articleWriteForm.getTitle();
        String newContent = articleWriteForm.getContent();
        Article updatedArticle = article.update(newTitle, newContent);

        articleDatabase.update(updatedArticle);
        logger.info("게시글 정보가 업데이트 되었습니다. {}", updatedArticle);
        return updatedArticle;
    }

    public Article delete(Long id) {
        Article article = getArticle(id);
        if (hasOtherWriterComment(article)) {
            throw new ArticleHasCommentsException(id);
        }
        articleDatabase.softDelete(id);
        article.delete();
        logger.info("게시글이 삭제 되었습니다. {}", article);
        return article;
    }

    public ArticleWriteForm getArticleUpdateForm(Long id) {
        Article article = getArticle(id);
        return new ArticleWriteForm(article.getTitle(), article.getContent());
    }

    public Article getArticle(Long id) {
        return articleDatabase.findBy(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private boolean hasOtherWriterComment(Article article) {
        String articleWriter = article.getWriter();
        return commentDatabase.findAll(article.getId())
                .stream()
                .anyMatch(comment -> !comment.hasSameWriter(articleWriter)); // 게시물의 작성자가 아닌 다른 유저가 쓴 댓글이 존재하는지 확인
    }

}
