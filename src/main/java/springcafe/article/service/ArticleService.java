package springcafe.article.service;


import org.springframework.stereotype.Service;
import springcafe.article.model.Article;
import springcafe.article.repository.ArticleRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ArticleService {

    private Long sequence =0L;


    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void create(String writer, String title, String contents){

        Article article = new Article(writer, title, contents, LocalDateTime.now(), sequence);
        sequence = sequence+1;
        this.articleRepository.insert(article);
    }

    public Article findById(Long id){
       return articleRepository.findById(id);

    }
    public Map<Long, Article> findAll(){
        return this.articleRepository.findAll();
    }

}
