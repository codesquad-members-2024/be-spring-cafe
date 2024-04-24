package codesquad.springcafe.repository.article;

import static org.assertj.core.api.Assertions.*;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.util.Page;
import codesquad.springcafe.util.PageRequest;
import codesquad.springcafe.util.Sort;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleRepositoryMySqlTest {

    @Autowired
    private ArticleRepository repository;

    @BeforeEach
    void setUp() {
        // repository 초기화
        repository.clear();

        // article 300개 추가
        for (int i = 0; i < 300; i++) {
            Article article = createArticle(i);
            repository.save(article);
        }
    }

    @DisplayName("0번 페이지 요청 시 요청 사이즈가 15 이면 15개의 게시글을 가져올 수 있다")
    @Test
    void findAll() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 15, Sort.sorted());

        // when
        Page<Article> page = repository.findAll(pageRequest);

        // then
        assertThat(page.getPageNumber()).isEqualTo(0);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.isLast()).isFalse();
        assertThat(page.hasNext()).isTrue();
        assertThat(page.getNumberOfElements()).isEqualTo(15);
        assertThat(page.getTotalElements()).isEqualTo(300);
        assertThat(page.getTotalPages()).isEqualTo(20);
    }

    private Article createArticle(int i) {
        Article article = new Article();
        article.setTitle("test title" + i);
        article.setContents("test contents" + i);
        article.setCreatedBy("testuser");
        article.setCreatedAt(LocalDateTime.now());
        return article;
    }
}