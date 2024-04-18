package codesquad.springcafe.interceptor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class LoginInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("로그인 상태가 아니면 질문하기, 질문 상세보기, 회원정보 리스트 페이지에 접속 시 로그인 페이지로 리다이렉션 된다")
    @Test
    void redirect_login() throws Exception {
        // given & when
        ResultActions questions = mockMvc.perform(get("/questions")); // 질문하기
        ResultActions detailQuestions = mockMvc.perform(get("/questions/1")); // 질문 상세보기
        ResultActions members = mockMvc.perform(get("/members")); // 회원정보 리스트

        // then
        questions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        detailQuestions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        members.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}