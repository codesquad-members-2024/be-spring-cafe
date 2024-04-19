package springcafe.article.service;


import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.article.repository.ArticleDao;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleService {

    private Long sequence =0L;


    private ArticleDao articleDao;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void create(String writer, String title, String contents){

        Article article = new Article(writer, title, contents, LocalDateTime.now(), sequence);
        sequence = sequence+1;
        this.articleDao.insert(article);
    }

    public Article findById(Long id){
       return articleDao.findById(id);

    }
    public List<Article> findAll(){
        return this.articleDao.findAll();
    }

}
