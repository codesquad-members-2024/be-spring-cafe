package springcafe.article.service;


import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.article.repository.ArticleDao;
import springcafe.reply.model.Reply;

import java.util.List;

@Service
public class ArticleService {

    private Long sequence =0L;


    private ArticleDao articleDao;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void create(String writer, String title, String contents, Long id){

        Article article = new Article(writer, title, contents);
        this.articleDao.save(article, id);
    }

    public Article findById(Long id){
       return articleDao.findById(id);

    }
    public List<Article> findAll(){
        return this.articleDao.findAll();
    }

    public void update(Long articleId, String newTitle, String newContents) {
        Article articleToUpdate = articleDao.findById(articleId);
        articleToUpdate.updateTitle(newTitle);
        articleToUpdate.updateContent(newContents);

        articleDao.update(articleToUpdate);

    }

    public void delete(Long articleId){
        articleDao.delete(articleId);
    }

    public boolean checkIfPossibleToDelete(List<Reply>replyList, String writer){
        if(replyList.isEmpty()){
                return true;
        }

        return replyList.stream().allMatch(reply->
                reply.matchesWriter(writer));

    }


}
