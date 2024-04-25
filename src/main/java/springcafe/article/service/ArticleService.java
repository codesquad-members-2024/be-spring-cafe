package springcafe.article.service;


import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.article.repository.ArticleDao;
import springcafe.reply.model.Reply;
import springcafe.reply.repository.ReplyDao;
import springcafe.user.exception.WrongWriterException;
import springcafe.user.model.User;

import java.util.List;

@Service
public class ArticleService {

    private ArticleDao articleDao;
    private ReplyDao replyDao;


    public ArticleService(ArticleDao articleDao, ReplyDao replyDao) {
        this.articleDao = articleDao;
        this.replyDao = replyDao;
    }

    public void saveArticle(String writer, String title, String contents, Long id){

        Article article = new Article(writer, title, contents);
        this.articleDao.save(article, id);
    }

    public Article findByArticleId(Long id){
       return articleDao.findById(id);

    }
    public List<Article> findAllArticles(){
        return this.articleDao.findAll();
    }

    public void update(Long articleId, String newTitle, String newContents) {
        Article articleToUpdate = articleDao.findById(articleId);
        articleToUpdate.updateTitle(newTitle);
        articleToUpdate.updateContent(newContents);

        articleDao.update(articleToUpdate);

    }

    public void deleteArticle(Long articleId, User user){
        Article articleToDelete = articleDao.findById(articleId);
        List<Reply> replyList = replyDao.findByArticleId(articleId);

        if (!articleToDelete.matchesWriter(user.getUserId())
                || !checkIfPossibleToDelete(replyList, articleToDelete.getWriter())) {
            throw new WrongWriterException("삭제 권한이 없습니다.");
        }

        articleDao.delete(articleId);
    }

    public boolean checkIfPossibleToDelete(List<Reply>replyList, String writer){
        return replyList.isEmpty() || replyList.stream().allMatch(reply -> reply.matchesWriter(writer));

    }
}
