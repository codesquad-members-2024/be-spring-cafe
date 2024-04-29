package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.Reply;
import codesquad.springcafe.dto.ArticleDto;
import codesquad.springcafe.error.exception.AccessDeniedException;
import codesquad.springcafe.error.exception.ArticleNotFoundException;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.repository.reply.ReplyRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    public void createArticle(Article article) {
        articleRepository.createArticle(article);
        logger.debug("ID {} 게시글 생성", article.getArticleId());
    }

    public List<Article> findAllArticles() {
        return articleRepository.findAllArticles();
    }

    public Article findByArticleId(long articleId) {
        Article article = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId + " ID 게시글이 존재하지 않습니다."));

        logger.debug("ID {} 게시글 조회", articleId);
        return article;
    }

    public void updateViews(long articleId) {
        articleRepository.updateViews(articleId);
        logger.debug("ID {} 게시글 조회수 증가", articleId);
    }

    public void updateArticle(long articleId, ArticleDto articleDto) {
        articleRepository.updateArticle(articleId, articleDto);
        logger.debug("ID {} 게시글 수정", articleId);
    }

    public void deleteArticle(long articleId) {
        Article article = articleRepository.findByArticleId(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId + " ID 게시글이 존재하지 않습니다."));

        List<Reply> replies = replyRepository.findRepliesByArticleId(articleId);

        if ((!replies.isEmpty()) && replies.stream()
                .anyMatch(reply -> !reply.getWriter().equals(article.getWriter()))) {
            throw new AccessDeniedException(articleId + " ID 게시글 삭제 불가");
        }

        articleRepository.deleteArticle(articleId);
        logger.debug("ID {} 게시글 삭제", articleId);
    }
}