package codesquad.springcafe.controller;

import codesquad.springcafe.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleRepository articleRepository;

    @Test
    @DisplayName("글 작성 폼으로 이동")
    void getQuestionFormTest() throws Exception {
        mockMvc.perform(get("/question"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("qna/form"))
                .andReturn();
    }

    @Test
    @DisplayName("게시글을 저장 후 홈페이지로 리다이렉트")
    void writeQuestionTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users")
                .param("writer", "cori")
                .param("title", "1234")
                .param("contents", "hello world");

        mockMvc.perform(post("/question"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }
}