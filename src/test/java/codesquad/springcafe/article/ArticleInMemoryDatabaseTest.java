package codesquad.springcafe.article;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleInMemoryDatabaseTest {

    @Autowired
    ArticleInMemoryDatabase articleInMemoryDatabase;

    @AfterEach
    void resetDB() {
        articleInMemoryDatabase.clear();
    }

    @Test
    @DisplayName("ArticleDatabase에 게시글이 잘 저장되는지 테스트")
    void saveArticle() {
        Article article = new ArticleBuilder().articleId(1).author("jayden").title("제목")
            .content("내용").build();

        articleInMemoryDatabase.saveArticle(article);
        assertThat(articleInMemoryDatabase.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("ArticleDatabase의 모든 게시글이 잘 찾아지는지 테스트")
    void testFindAllArticles() {

        Article article = new ArticleBuilder().articleId(1).author("jayden").title("제목")
            .content("내용").build();

        articleInMemoryDatabase.saveArticle(article);
        assertThat(articleInMemoryDatabase.findAll()).hasSize(1);
    }
}