package codesquad.springcafe.controller;

import codesquad.springcafe.WebConfig;
import codesquad.springcafe.db.ArticleDatabase;
import codesquad.springcafe.model.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ArticleController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = WebConfig.class))
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleDatabase articleDatabase;

    @BeforeEach
    void setUp(){
        Article article1 = new Article();
        article1.setSequence(1L);
        article1.setTitle("title1");
        article1.setContent("content1");

        Article article2 = new Article();
        article2.setSequence(2L);
        article2.setTitle("title2");
        article2.setContent("content2");
        List<Article> articles = Arrays.asList(article1, article2);

        willDoNothing().given(articleDatabase).addArticle(article1);
        given(articleDatabase.findAllArticles()).willReturn(articles);
        given(articleDatabase.getTotalArticleNumber()).willReturn(articles.size());
        given(articleDatabase.findArticleBySequence(1L)).willReturn(Optional.of(article1));
        given(articleDatabase.findArticleBySequence(2L)).willReturn(Optional.of(article2));
    }


    @DisplayName("게시물 작성에 성공하면 '/'로 리다이렉션 된다")
    @Test
    void createPostRedirectionTest() throws Exception {
        String title = "test_title";
        String content = "test_content";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/articles")
                .param("title", title)
                .param("content", content);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    @DisplayName("존재하지 않는 게시물 sequence로 GET '/articles/{sequence}' 요청을 보내면 NoSuchElementException을 반환한다")
    @Test
    void loadArticleContentExceptionTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/3"))
                .andExpect(status().is4xxClientError());
    }


    @DisplayName("존재하는 sequence로 GET '/articles/{sequence}' 요청할 경우 post/show 뷰 템플릿을 반환한다")
    @Test
    void loadArticleContentSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/articles/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/show"));
    }
}
