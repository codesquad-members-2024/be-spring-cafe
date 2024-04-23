package codesquad.springcafe.article;

import codesquad.springcafe.article.dto.ArticleUpdateRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ArticleService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ArticleDao articleDao;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public Optional<Article> findArticle(int id) {
        return articleDao.findBy(id);
    }

    public void save(ArticleCraetionDto dto) {
        Article article = dto.toEntity();
        log.debug("들어온 게시글 : {}", article);
        articleDao.save(article);
    }

    public Collection<Article> getAllArticles() {
        return articleDao.findAll();
    }

    public void updateArticle(String writer, ArticleUpdateRequestDto dto) {
        Article article = dto.toEntity(writer);
        articleDao.update(article);
    }
}
