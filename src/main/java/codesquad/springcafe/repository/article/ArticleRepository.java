package codesquad.springcafe.repository.article;

import codesquad.springcafe.domain.Article;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepository implements ArticleRepositoryInterface {
    private static final List<Article> articles = new ArrayList<>();


}
