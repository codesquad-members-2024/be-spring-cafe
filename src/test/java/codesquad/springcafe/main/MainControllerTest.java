package codesquad.springcafe.main;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import codesquad.springcafe.article.Article;
import codesquad.springcafe.article.ArticleService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleService articleService;

    @Test
    @DisplayName("게시글이 2개 저장될 때 index 페이지 테스트")
    void mainIndexTest() throws Exception {
        //Given
        Article article1 = new Article((long) 1, "title1", "author1", "contents1",
            Timestamp.valueOf(LocalDateTime.now()),
            false);
        Article article2 = new Article((long) 2, "title2", "author2", "contents2",
            Timestamp.valueOf(LocalDateTime.now()),
            false);

        //when & then
        when(articleService.findAll()).thenReturn(Arrays.asList(article1, article2));
        mockMvc.perform(get("/")).andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attribute("articles", Arrays.asList(article1, article2)))
            .andExpect(model().attributeExists("articles"));
    }
}