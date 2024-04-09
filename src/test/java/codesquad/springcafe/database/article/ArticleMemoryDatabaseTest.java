package codesquad.springcafe.database.article;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.model.Article;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleMemoryDatabaseTest {

    ArticleMemoryDatabase articleMemoryDatabase = new ArticleMemoryDatabase();

    @Test
    @DisplayName("id와 일치하는 Article을 찾을 수 있다.")
    void findById() {
        Article article = new Article("상추", "타이틀1", "내용입니다.");
        articleMemoryDatabase.save(article);
        Article result = articleMemoryDatabase.findBy(1L).get();
        assertThat(result.getWriter()).isEqualTo("상추");
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("id와 일치하는 Article이 존재하지 않으면 빈 결과를 반환한다.")
    void findByIdFailed() {
        Article article = new Article("상추", "타이틀1", "내용입니다.");
        articleMemoryDatabase.save(article);
        Optional<Article> result = articleMemoryDatabase.findBy(2L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("데이터베이스에 저장된 모든 Article을 확인할 수 있다.")
    void findAll() {
        Article article1 = new Article("상추", "타이틀1", "내용입니다.");
        Article article2 = new Article("배추", "타이틀2", "내용입니다.");
        articleMemoryDatabase.save(article1);
        articleMemoryDatabase.save(article2);
        List<Article> result = articleMemoryDatabase.findAll();

        assertThat(result).contains(article1, article2);
    }
}