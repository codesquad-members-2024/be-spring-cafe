package codesquad.springcafe.ArticleTest;

import codesquad.springcafe.Article.Article;
import codesquad.springcafe.Article.MemoryArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class MemoryArticleRepositoryTest {

    private MemoryArticleRepository memoryArticleRepository;

    @BeforeEach
    void setUp() {
        memoryArticleRepository = new MemoryArticleRepository();}
    @AfterEach
    void tearDown() { memoryArticleRepository.clear();}

    @Test
    @DisplayName("새 글 추가 테스트")
    void addArticle() {
        Article article = new Article("작성자", "제목", "내용");
        Article savedArticle = memoryArticleRepository.addArticle(article);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getTitle()).isEqualTo("제목");
        assertThat(savedArticle.getContents()).isEqualTo("내용");
        assertThat(savedArticle.getWriter()).isEqualTo("작성자");
    }

    @Test
    @DisplayName("전체 게시글 조회 테스트")
    void findAllArticles() {
        Article article1 = new Article("작성자1", "제목1", "내용1");
        Article article2 = new Article("작성자2", "제목2", "내용2");
        memoryArticleRepository.addArticle(article1);
        memoryArticleRepository.addArticle(article2);

        assertThat(memoryArticleRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("인덱스로 게시글 찾기 테스트")
    void findArticleByIndex() {
        Article article = new Article("작성자", "제목", "내용");
        memoryArticleRepository.addArticle(article);

        assertThat(memoryArticleRepository.findByIndex(1)).isNotEmpty();
        assertThat(memoryArticleRepository.findByIndex(1).get().getTitle()).isEqualTo("제목");
    }

    @Test
    @DisplayName("유효하지 않은 인덱스로 게시글 찾기 실패 테스트")
    void findArticleByInvalidIndex() {
        Article article = new Article("작성자", "제목", "내용");
        memoryArticleRepository.addArticle(article);

        assertThat(memoryArticleRepository.findByIndex(-1)).isEmpty();
        assertThat(memoryArticleRepository.findByIndex(2)).isEmpty();
    }

    @Test
    @DisplayName("저장된 게시글 수 확인 테스트")
    void articleSize() {
        Article article1 = new Article("작성자1", "제목1", "내용1");
        Article article2 = new Article("작성자2", "제목2", "내용2");
        memoryArticleRepository.addArticle(article1);
        memoryArticleRepository.addArticle(article2);

        assertThat(memoryArticleRepository.articleSize()).isEqualTo(2);
    }
}
