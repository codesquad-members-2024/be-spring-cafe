package codesquad.springcafe.database.article;

import static org.assertj.core.api.Assertions.assertThat;

import codesquad.springcafe.model.Article;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleDatabaseTest {

    @Autowired
    ArticleDatabase articleDatabase;

    @BeforeEach
    void setUp() {
        articleDatabase.clear();
    }

    @Test
    @DisplayName("사용자가 작성한 article을 데이터베이스에 저장할 수 있다.")
    void add() {
        Article article = new Article("상추", "제목", "본문");
        Long id = articleDatabase.add(article).getId();

        Article result = articleDatabase.findBy(id).get();
        assertThat(result).isEqualTo(article);
    }

    @Test
    @DisplayName("데이터베이스에 존재하는 모든 article을 조회할 수 있다.")
    void findAll() {
        Article article1 = new Article("상추", "제목1", "본문1");
        Article article2 = new Article("배추", "제목2", "본문2");
        articleDatabase.add(article1);
        articleDatabase.add(article2);

        List<Article> result = articleDatabase.findAll();
        assertThat(result).containsExactly(article1, article2);
    }

    @Test
    @DisplayName("아티클의 정보를 수정하고 저장할 수 있다.")
    void update() {
        Article article = new Article("상추", "제목1", "본문1");
        Long id = articleDatabase.add(article).getId();

        article.increaseViews();
        articleDatabase.update(article);

        Article result = articleDatabase.findBy(id).get();

        assertThat(result.getViews()).isEqualTo(1);
    }
}