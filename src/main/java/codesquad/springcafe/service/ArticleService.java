package codesquad.springcafe.service;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.comment.CommentDatabase;
import codesquad.springcafe.exception.ArticleAccessException;
import codesquad.springcafe.exception.ArticleHasCommentsException;
import codesquad.springcafe.exception.ArticleNotFoundException;
import codesquad.springcafe.exception.InvalidSearchTypeException;
import codesquad.springcafe.form.article.ArticleWriteForm;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.web.Search;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    public static final int articlesPerPage = 15;
    private final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleDatabase articleDatabase;
    private final CommentDatabase commentDatabase;

    public ArticleService(ArticleDatabase articleDatabase, CommentDatabase commentDatabase) {
        this.articleDatabase = articleDatabase;
        this.commentDatabase = commentDatabase;
    }

    public Article writeArticle(ArticleWriteForm articleWriteForm, String nickname) {
        Article article = new Article(nickname, articleWriteForm.getTitle(),
                articleWriteForm.getContent(), LocalDateTime.now());
        articleDatabase.add(article);
        logger.info("새로운 게시물이 추가되었습니다. {}", article);
        return article;
    }

    public List<Article> getArticlesByPage(Long page) {
        Long offset = (page - 1) * articlesPerPage;
        return articleDatabase.findPageArticles(offset, articlesPerPage);
    }

    public Article viewArticle(Long id) {
        Article article = findArticle(id);
        articleDatabase.increaseViews(id);
        article.increaseViews();
        return article;
    }


    public Article updateArticle(Long id, ArticleWriteForm articleWriteForm, String writer) {
        validateAccess(id, writer);
        Article article = findArticle(id);

        String newTitle = articleWriteForm.getTitle();
        String newContent = articleWriteForm.getContent();
        Article updatedArticle = article.update(newTitle, newContent);

        articleDatabase.update(updatedArticle);
        logger.info("게시글 정보가 업데이트 되었습니다. {}", updatedArticle);
        return updatedArticle;
    }

    /**
     * 유저의 리소스 접근권 확인 및 다른 사용자의 댓글이 없는지 확인한 후 삭제한다.
     */
    public Article deleteArticle(Long id, String userNickname) {
        Article article = findArticle(id);
        validateAccess(id, userNickname);
        validateOtherComment(id, userNickname);

        articleDatabase.softDelete(id);
        commentDatabase.softDeleteComments(id);
        article.delete();
        logger.info("게시글이 삭제 되었습니다. {}", article);
        return article;
    }

    public ArticleWriteForm getArticleUpdateForm(Long id) {
        Article article = findArticle(id);
        return new ArticleWriteForm(article.getTitle(), article.getContent());
    }

    public Article findArticle(Long id) {
        return articleDatabase.findBy(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public int getTotalPages(Long totalArticleSize) {
        return (int) Math.ceil((double) totalArticleSize / articlesPerPage);
    }

    public Long getSearchedCount() {
        return articleDatabase.countTotalArticles();
    }

    public void validateAccess(Long id, String userNickname) {
        String writer = articleDatabase.findWriter(id);
        if (!writer.equals(userNickname)) {
            throw new ArticleAccessException();
        }
    }

    public void validateOtherComment(Long articleId, String userNickname) {
        List<String> commentWriters = commentDatabase.findWriters(articleId);
        boolean hasOtherComment = commentWriters.stream()
                .anyMatch(commentWriter -> !commentWriter.equals(userNickname));
        if (hasOtherComment) {
            throw new ArticleHasCommentsException(articleId);
        }
    }

    public Long getSearchedCount(Search search) {
        String keyword = search.getKeyword();
        if (search.isTitleContentType()) {
            return articleDatabase.countSearchedArticlesByTitleContent(keyword);
        }
        if (search.isWriterType()) {
            return articleDatabase.countSearchedArticlesByWriter(keyword);
        }
        throw new InvalidSearchTypeException();
    }

    public List<Article> getSearchedArticlesByPage(Search search, Long page) {
        String keyword = search.getKeyword();
        Long offset = (page - 1) * articlesPerPage;

        if (search.isTitleContentType()) {
            return articleDatabase.findSearchedPageArticlesByTitleContent(keyword, offset, articlesPerPage);
        }
        if (search.isWriterType()) {
            return articleDatabase.findSearchedPageArticlesByWriter(keyword, offset, articlesPerPage);
        }
        throw new InvalidSearchTypeException();
    }
}
