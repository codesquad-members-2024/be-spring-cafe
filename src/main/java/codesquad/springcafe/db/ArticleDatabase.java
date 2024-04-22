package codesquad.springcafe.db;

import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ArticleDatabase {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDatabase.class);

    private Map<Long, Article> articles = new LinkedHashMap<>();

    private Map<Long, Reply> replies = new LinkedHashMap<>();

    public long getReplyId() {   // Article에 ID를 주기 위한 메소드
        return replies.size() + 1;    // index는 1번부터 시작하도록 +1
    }

    public long getArticleID() {   // Article에 ID를 주기 위한 메소드
        return articles.size() + 1;    // index는 1번부터 시작하도록 +1
    }

    public void addReply(Reply reply) {
        long replyId = getReplyId();
        reply.setReplyId(replyId);
        replies.put(replyId, reply);
        logger.debug("Reply # {} ,[{}] Added at Memory DB", replyId, reply.getComment());
    }

    public ArrayList<Reply> getReplies(long articleId) {
        return replies.values().stream()
                .filter(reply -> reply.getArticleId() == articleId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addArticle(Article article) {
        long articleId = getArticleID();
        article.setArticleId(articleId);    // memory에서는 직접 setter를 통해 할당
        articles.put(articleId, article);
        logger.debug("Article # {} ,[{}] Added at Memory DB", articleId, article);
    }

    public ArrayList<Article> getAllArticles() {
        return new ArrayList<>(articles.values());
    }

    public Article findArticleById(long articleId) {
        if (articleId >= 0 && articleId <= articles.size()) {
            return articles.get(articleId);
        }
        return null;
    }

    public void updatePageView(long articleId) {
        Article article = articles.get(articleId);
        article.setPageViews(article.getPageViews() + 1);
        logger.debug("Article with ID: '{}' has its page views incremented", articleId);
    }

    public void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto) {
        Article article = articles.get(articleId);
        article.setTitle(articleUpdateDto.getTitle());
        article.setContent(articleUpdateDto.getContent());
        logger.debug("Article With ID : '{}' Updated", articleId);
    }

    public void deleteArticle(long articleId) {
        articles.remove(articleId);
        logger.debug("Article ID '{}' Deleted", articleId);
    }
}
