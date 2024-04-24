package springcafe.reply.repository;

import springcafe.article.model.Article;
import springcafe.reply.model.Reply;

import java.util.List;

public interface ReplyDao {

    public void save(Article article, String content, Reply reply);
    public List<Reply> findByArticleId(Long articleId);
    public Reply findByReplyId(Long replyId);
    public void delete(Long replyId);


}
