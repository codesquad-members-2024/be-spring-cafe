package codesquad.springcafe.articles.repository;

import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.Reply;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.articles.model.dto.ReplyCreationRequest;

import java.util.ArrayList;
import java.util.Optional;

public interface ArticleRepository {
    void createArticle(Article article);

    Optional<ArrayList<Article>> getAllArticles();

    Optional<Article> findArticleById(long articleId);

    void incrementPageView(long articleId);

    void updateArticle(long articleId, ArticleUpdateDto articleUpdateDto);

    void deleteArticle(long articleId);

    Reply createReply(Reply reply);

    Optional<ArrayList<Reply>> getReplies(long articleId);

    Optional<Reply> findReplyById(long replyId);

    boolean deleteReply(long replyId);
}
