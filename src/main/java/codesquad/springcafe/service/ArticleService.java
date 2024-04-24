package codesquad.springcafe.service;

import codesquad.springcafe.dto.ArticleRequestDto;
import codesquad.springcafe.exception.ArticleNotFountException;
import codesquad.springcafe.exception.CannotDeleteArticleWithRepliesException;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.ArticleRepository;
import codesquad.springcafe.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    public ArticleService(ArticleRepository articleRepository, ReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    public void createArticle(Article article) {
        articleRepository.save(article);
    }

    public Article findById(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isPresent()) {
            return optionalArticle.get();
        }
        throw new ArticleNotFountException();
    }

    public boolean checkArticleWriter(Long articleId, String userId) {
        Article article = findById(articleId);
        return article.checkWriter(userId);
    }

    public void update(Long articleId, ArticleRequestDto articleRequestDto) {
        articleRepository.update(articleId, articleRequestDto);
    }

    public void delete(Long articleId) {
        List<Reply> replies = replyRepository.findRepliesByArticleId(articleId);
        if (!replies.isEmpty()) {
            throw new CannotDeleteArticleWithRepliesException();
        }
        articleRepository.delete(articleId);
    }
}
