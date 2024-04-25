package springcafe.article.service;


import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.article.repository.ArticleDao;
import springcafe.reply.model.Reply;

import java.util.List;

@Service
public class ArticleService {

    private ArticleDao articleDao;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
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

    public void deleteArticle(Long articleId){
        articleDao.delete(articleId);
    }

    public boolean checkIfPossibleToDelete(List<Reply>replyList, String writer){
        return replyList.isEmpty() || replyList.stream().allMatch(reply -> reply.matchesWriter(writer));

    }
}
