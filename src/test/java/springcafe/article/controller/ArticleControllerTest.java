package springcafe.article.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import springcafe.article.model.Article;
import springcafe.article.repository.ArticleDao;
import springcafe.article.service.ArticleService;
import springcafe.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {


    @Autowired
    MockMvc mockMvc;
    ArticleDao articleDao;
    private ArticleService articleService;

    public ArticleControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setup() {
        articleDao = Mockito.mock(ArticleDao.class);
        articleService = new ArticleService(articleDao);
        Mockito.reset(articleDao); // 모킹된 DAO의 상태를 초기화
    }

    @DisplayName("questionCreate: 게시판 글 추가에 성공한다.")
    @Test
    public void addArticles() throws Exception {


        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", new User("fnelclsrn", "123", "정연호", "fnelclsrn123@naver.com"));

        mockMvc.perform(post("/qna/questions").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .session(mockHttpSession)
                        .param("title", "제목")
                        .param("contents", "내용"))
                .andExpect(status().is3xxRedirection());
        Mockito.when(articleDao.findAll()).thenReturn(Collections.singletonList(new Article("fnelclsrn", "제목", "내용", LocalDateTime.now(), 1L, 2L)));

        List<Article> all = articleDao.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getTitle()).isEqualTo("제목");
        assertThat(all.get(0).getWriter()).isEqualTo("fnelclsrn");
        assertThat(all.get(0).getContents()).isEqualTo("내용");

    }

    @DisplayName("questionCreate: 글의 내용을 비어두면 테스트가 실패해서 다시 글 작성 페이지로 리다이렉션된다.")
    @Test
    public void addArticles2() throws Exception {


        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", new User("fnelclsrn", "123", "정연호", "fnelclsrn123@naver.com"));

        mockMvc.perform(post("/qna/questions").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .session(mockHttpSession)
                        .param("title", "제목")
                        .param("contents", ""))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("qna/form"))
                .andExpect(model().attributeHasFieldErrors("article", "contents"));

    }

    @DisplayName("questionCreate: 제목을 비어두면 테스트가 실패해서 다시 글 작성 페이지로 리다이렉션된다.")
    @Test
    public void addArticles3() throws Exception {


        MockHttpSession mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", new User("fnelclsrn", "123", "정연호", "fnelclsrn123@naver.com"));

        mockMvc.perform(post("/qna/questions").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .session(mockHttpSession)
                        .param("title", "")
                        .param("contents", "내용"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("qna/form"))
                .andExpect(model().attributeHasFieldErrors("article", "title"));

    }

    @DisplayName("questionCreate: 로그인하지 않고 글 작성 페이지에 들어가면 로그인 페이지로 리다리엑션된다.")
    @Test
    public void addArticles4() throws Exception {


        mockMvc.perform(post("/qna/questions").contentType(MediaType.APPLICATION_FORM_URLENCODED)

                        .param("title", "제목")
                        .param("contents", "내용"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));

    }


}

