package codesquad.springcafe.repository.article;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    void save(Article article);

    Optional<Article> getById(Long id);

    List<Reply> getRepliesById(Long id);

    List<Article> getAll();

    void modify(Article modifiedArticle);

    void removeHard(Long id);

    void removeSoft(Long id);
}
