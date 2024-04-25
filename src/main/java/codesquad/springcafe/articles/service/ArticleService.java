package codesquad.springcafe.articles.service;

import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.model.dto.ReplyCreationRequest;
import codesquad.springcafe.articles.model.dto.ReplyViewDto;
import codesquad.springcafe.articles.repository.ArticleRepository;
import codesquad.springcafe.exception.ArticleAccessException;
import codesquad.springcafe.exception.ArticleNotFoundException;
import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.exception.ReplyNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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
        articleRepository.incrementPageView(articleId);
    }

    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        articleRepository.updateArticle(articleId, articleUpdateDto);
    }

    public void deleteArticle(long articleId) {
        Article article = articleRepository.findArticleById(articleId).orElseThrow(() -> new ArticleNotFoundException("게시글을 찾을 수 없습니다."));

        ArrayList<Reply> replies = articleRepository.getReplies(articleId).orElseThrow(() -> new ReplyNotFoundException("댓글을 찾을 수 업습니다."));

        validateArticleAccess(replies, article.getUserId());

        articleRepository.deleteArticle(articleId);
    }

    public Reply createReply(long articleId, ReplyCreationRequest replyCreationRequest) {
        Reply reply = new Reply(articleId, replyCreationRequest.getWriter(), replyCreationRequest.getComment());

        return articleRepository.createReply(reply);
    }

    public ArrayList<ReplyViewDto> getReplies(String sessionedUserId, long articleId) {
        ArrayList<Reply> replies = articleRepository.getReplies(articleId).orElseThrow(() -> new ReplyNotFoundException("댓글을 찾을 수 없습니다."));

        ArrayList<ReplyViewDto> replyViews = new ArrayList<>(replies.size());

        for (Reply reply : replies) {
            boolean editRight = reply.getUserId().equals(sessionedUserId);
            ReplyViewDto replyViewDto = new ReplyViewDto(reply, editRight);
            replyViews.add(replyViewDto);
        }

        return replyViews;
    }

    public Reply findReplyById(long replyId) {
        return articleRepository.findReplyById(replyId).orElseThrow(() -> new ReplyNotFoundException("해당하는 댓글을 찾을 수 없습니다."));
    }

    public void deleteReply(long replyId) {
        articleRepository.deleteReply(replyId);
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
