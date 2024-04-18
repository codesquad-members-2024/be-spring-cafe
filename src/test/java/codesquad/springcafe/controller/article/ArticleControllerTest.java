package codesquad.springcafe.controller.article;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import codesquad.springcafe.domain.article.Article;
import codesquad.springcafe.repository.article.ArticleRepository;
import codesquad.springcafe.service.article.ArticleService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    private static final String BASE_URL = "/questions";

    @Test
    void get_request_publish() throws Exception {
        // given & when
        ResultActions result = mockMvc.perform(get(BASE_URL));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("qna/form"));
    }

    @DisplayName("제목이 testTitle, 본문 내용이 testContents, 작성자가 testUser 인 게시물은 발행에 성공한다")
    @Test
    void publish_success_when_post_request_with_article() throws Exception {
        // given
        String articleParams = "title=testTitle&createdBy=testUser&contents=testContents";

        // when
        ResultActions result = mockMvc.perform(post(BASE_URL)
                .content(articleParams)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        );

        // then
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @DisplayName("제목, 본문, 작성자 3개 항목 중 하나라도 빈 값이 들어가면 발행에 실패하고, 바인딩에 실패한 field error를 Model에 담는다")
    @Test
    void publish_fail_when_post_request_with_empty_field() throws Exception {
        // given
        String noTitle = "createdBy=testUser&contents=testContents";
        String noContents = "title=testTitle&createdBy=testUser";
        String noCreatedBy = "title=testTitle&contents=testContents";

        // when
        // 제목에 빈 값이 들어갈 때
        ResultActions noTitleResult = mockMvc.perform(post(BASE_URL)
                .content(noTitle)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // 본문에 빈 값이 들어갈 때
        ResultActions noContentsResult = mockMvc.perform(post(BASE_URL)
                .content(noContents)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // 작성자에 빈 값이 들어갈 때
        ResultActions noCreatedByResult = mockMvc.perform(post(BASE_URL)
                .content(noCreatedBy)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // then
        noTitleResult.andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("article", "title"));

        noContentsResult.andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("article", "contents"));

        noCreatedByResult.andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeHasFieldErrors("article", "createdBy"));
    }

    @DisplayName("존재하는 'question/1' URI는 Model에 lineSeparator, article을 담는다")
    @Test
    void detail_success() throws Exception {
        // given
        Article article = new Article();
        article.setCreatedAt(LocalDateTime.now());
        articleRepository.save(article);

        // when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/1"));

        // then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("lineSeparator", "article"))
                .andExpect(view().name("qna/detail"));
    }

    @DisplayName("존재하지 않는 'question/1' URI는 'error/404'로 리다이렉션 된다")
    @Test
    void detail_fail() throws Exception {
        // given & when
        ResultActions result = mockMvc.perform(get(BASE_URL + "/1"));

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("error/404"));
    }
}