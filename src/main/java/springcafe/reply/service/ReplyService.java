package springcafe.reply.service;

import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.reply.model.Reply;
import springcafe.reply.repository.ReplyDao;
import springcafe.user.exception.WrongWriterException;

import java.util.List;

@Service
public class ReplyService {

    private final ReplyDao replyDao;

    public ReplyService(ReplyDao replyDao) {
        this.replyDao = replyDao;
    }


    public void saveReply(Article article, String content, String writer) {
        Reply reply = new Reply(content, article.getId(), writer);
        replyDao.save(article, content, reply);
    }


    public List<Reply> findReplyByArticleId(Long articleId){
        return replyDao.findByArticleId(articleId);

    }

    public Reply findByReplyId(Long replyId){
        return replyDao.findByReplyId(replyId);
    }

    public Long deleteReply(Long replyId, String userId){

        Reply replyToDelete = replyDao.findByReplyId(replyId);
        if(replyToDelete == null|| replyToDelete.matchesWriter(userId)){
            throw new WrongWriterException("권한이 없습니다.");
        }
        replyDao.delete(replyId);

        return replyToDelete.getArticleId();// 삭제한 댓글의 게시글로 돌아가도록 articleId 반환
    }

}
