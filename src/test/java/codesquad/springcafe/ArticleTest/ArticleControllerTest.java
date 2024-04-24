package codesquad.springcafe.ArticleTest;

import codesquad.springcafe.Article.Article;
import codesquad.springcafe.Article.ArticleController;
import codesquad.springcafe.Article.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시글 작성 폼 요청")
    void requestArticleForm() throws Exception {
        mockMvc.perform(get("/article/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("article/form"));
    }

    @Test
    @DisplayName("게시글 생성")
    void createArticle() throws Exception {
        mockMvc.perform(post("/questions")
                        .param("작성자", "작성자 이름")
                        .param("제목", "글 제목")
                        .param("내용", "글 내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void listArticles() throws Exception {
        given(articleRepository.findAll()).willReturn(Arrays.asList(
                new Article("작성자1", "제목1", "내용1"),
                new Article("작성자2", "제목2", "내용2")
        ));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("articles"));
    }

    @Test
    @DisplayName("게시글 상세 조회")
    void detailArticle() throws Exception {
        int articleNumber = 1;
        given(articleRepository.findByIndex(articleNumber)).willReturn(java.util.Optional.of(new Article("작성자", "제목", "내용")));

        mockMvc.perform(get("/article/{articleNumber}", articleNumber))
                .andExpect(status().isOk())
                .andExpect(view().name("article/show"))
                .andExpect(model().attributeExists("article"));
    }
}
