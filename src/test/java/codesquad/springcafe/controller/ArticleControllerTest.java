package codesquad.springcafe.controller;

import codesquad.springcafe.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    @Test
    @DisplayName("글 작성 폼으로 이동")
    void getQuestionFormTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUserId", "cori123");

        mockMvc.perform(get("/question").session(session))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("qna/form"))
                .andReturn();
    }

    @Test
    @DisplayName("게시글을 저장 후 홈페이지로 리다이렉트")
    void writeQuestionTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUserId", "cori123");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/question")
                .param("title", "1234")
                .param("contents", "hello world")
                .session(session);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }
}