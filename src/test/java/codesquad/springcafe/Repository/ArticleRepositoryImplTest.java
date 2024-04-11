package codesquad.springcafe.Repository;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.Domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleRepositoryImplTest {
    private ArticleRepository articleRepository;
    private Article article;
    @BeforeEach
    public void beforeEach() {
        articleRepository = new ArticleRepositoryImpl();

        article = new Article();
        article.setTitle("testTitle");
        article.setContent("hello,world!");
    }
    @Test
    @DisplayName("save가 잘 되는지 확인!")
    void save() {
        articleRepository.save(article);
        boolean isArticle = articleRepository.findById(1L).isPresent();
        assertThat(isArticle).isTrue();

        Article article2 = new Article();
        article2.setTitle("testTitle2");
        article2.setContent("hello,java!");
        articleRepository.save(article2);
        boolean isArticle2 = articleRepository.findById(2L).isPresent();
        assertThat(isArticle2).isTrue();
    }

    @Test
    @DisplayName("findById로 article이 잘 찾아와 지는지 확인")
    void findById() {
        articleRepository.save(article);
        Article article = articleRepository.findById(1L).get();
        assertThat(article.getId()).isEqualTo(1L);
        assertThat(article.getTitle()).isEqualTo("testTitle");
    }
}