package codesquad.springcafe.articles.service;

import codesquad.springcafe.replies.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.repository.ArticleRepository;
import codesquad.springcafe.exception.ArticleAccessException;
import codesquad.springcafe.exception.ArticleNotFoundException;
import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.exception.ReplyNotFoundException;
import codesquad.springcafe.replies.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ReplyRepository replyRepository) {
        this.articleRepository = articleRepository;
        this.replyRepository = replyRepository;
    }

    public void createArticle(ArticleCreationRequest articleCreationRequest) {
        Article article = new Article(articleCreationRequest.getUserId(), articleCreationRequest.getTitle(), articleCreationRequest.getContent());
        articleRepository.createArticle(article);
    }

    public ArrayList<Article> getAllArticles() {
        Optional<ArrayList<Article>> articles = articleRepository.getAllArticles();
        return articles.orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public Article findArticleById(long articleId) {
        Optional<Article> articleContent = articleRepository.findArticleById(articleId);
        return articleContent.orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));
    }

    public void incrementPageViews(long articleId) {
        if (articleRepository.findArticleById(articleId).isPresent()) { // articleID에 해당하는 Article이 DB에 저장되어 있는 경우에만 조회수 증가
            articleRepository.incrementPageView(articleId);
        }
    }

    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        articleRepository.updateArticle(articleId, articleUpdateDto);
    }

    public void deleteArticle(long articleId) {
        Article article = articleRepository.findArticleById(articleId).orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));

        ArrayList<Reply> replies = replyRepository.getReplies(articleId).orElseThrow(() -> new ReplyNotFoundException("댓글을 찾을 수 업습니다."));

        validateArticleAccess(replies, article.getUserId());

        articleRepository.deleteArticle(articleId);
    }

    private void validateArticleAccess(ArrayList<Reply> replies, String userId) {
        // * 댓글이 없는 경우
        if (replies.isEmpty()) {
            return;
        }
        // * 댓글이 있지만, 모든 게시글의 저자가 자신인 경우
        if (replies.stream().anyMatch(reply -> !reply.getUserId().equals(userId))) {
            throw new ArticleAccessException("게시글을 삭제할 수 없습니다. [에러 내용] : 작성자가 다른 댓글이 있는 경우 삭제가 불가능합니다.");
        }
    }
}
