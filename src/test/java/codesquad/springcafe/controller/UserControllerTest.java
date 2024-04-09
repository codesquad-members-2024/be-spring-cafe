package codesquad.springcafe.controller;

import codesquad.springcafe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입을 하면 /users로 리다이렉트해야한다.")
    public void testRegister() throws Exception {
        // 테스트 데이터 준비
        String userId = "test_user";
        String password = "test_password";
        String name = "테스트 유저";
        String email = "test@example.com";

        // POST 요청 생성
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users")
                .param("userId", userId)
                .param("password", password)
                .param("name", name)
                .param("email", email);

        // 요청 실행 및 응답 검증
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("/user로 get 요청이 들어오면 user/list 페이지로 포워딩한다.")
    void userListTest() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/users");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        ModelAndView mv = result.getModelAndView();
        String viewName = mv.getViewName();

        assertThat(viewName).isEqualTo("user/list");
    }

}