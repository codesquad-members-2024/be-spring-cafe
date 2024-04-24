package springcafe.reply.service;

import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.reply.model.Reply;
import springcafe.reply.repository.ReplyDao;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyDao replyDao;

    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }


    public void create(Article article, String content, String writer){
        Reply reply = new Reply(content, article.getId(), writer);
        replyDao.save(article, content, reply);
    }


    public List<Reply> findReplyByArticleId(Long articleId){
        return replyDao.findByArticleId(articleId);

    }

    public Reply findByReplyId(Long replyId){
        return replyDao.findByReplyId(replyId);
    }

    public void delete(Long replyId){
        replyDao.delete(replyId);
    }

}
