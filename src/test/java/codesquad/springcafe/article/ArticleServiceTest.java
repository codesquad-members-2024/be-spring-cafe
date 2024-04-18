package codesquad.springcafe.article;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import codesquad.springcafe.article.database.ArticleDatabase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private ArticleDatabase articleDatabase;

    @InjectMocks
    private ArticleService articleService;

    Article article1;
    Article article2;

    @BeforeEach
    void setUp() {
        article1 = new Article(1L, "title", "author", "contents");
    }


    @Test
    @DisplayName("articleId로 게시글을 찾는 테스트")
    void testFindById() {
        when(articleDatabase.findById(1L)).thenReturn(article1);

        Article foundArticle = articleService.findById(1L);

        assertThat(foundArticle).isEqualTo(article1);
        verify(articleDatabase, times(1)).findById(1L);
    }

    @Test
    @DisplayName("모든 게시글을 DB에서 가져오는지 확인")
    void testFindAll() {
        List<Article> articles = Arrays.asList(article1, article2);
        when(articleDatabase.findAll()).thenReturn(articles);

        List<Article> foundArticles = articleService.findAll();

        assertThat(foundArticles).isEqualTo(articles);
        verify(articleDatabase, times(1)).findAll();

    }

    @Test
    void testUpdate() {
        
    }
}
