package codesquad.springcafe.service;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.repository.ArticleRepository;
import codesquad.springcafe.repository.MemoryArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    // 레포를 mock 으로 선언하기
    @Mock
    private ArticleRepository articleRepository;

    // mock 객체를 서비스에 주입하기
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    @DisplayName("게시물 작성 시, 게시물을 정상 등록하고 등륵된 게시물을 올바르게 반환한다.")
    void testCreateArticle(){
        Article article = new Article("mango", "title", "content");
        when(articleRepository.save(any(Article.class))).thenReturn(article);

        Article createdArticle = articleService.createArticle(article);

        assertNotNull(createdArticle);
        assertThat(createdArticle.getWriter()).isEqualTo("mango");
    }

    @Test
    @DisplayName("id로 등록된 게시물을 잘 찾는지 확인한다.")
    void testFindUserById() {
        Optional<Article> optionalArticle = Optional.of(new Article("mango", "title", "content"));
        when(articleRepository.findById(anyLong())).thenReturn(optionalArticle);

        Optional<Article> foundArticle = articleService.findArticleById(1L);

        assertThat(foundArticle.get().getWriter()).isEqualTo("mango");
    }
}