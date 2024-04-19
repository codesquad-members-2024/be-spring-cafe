package codesquad.springcafe.controller;

import codesquad.springcafe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입을 하면 /users로 리다이렉트해야한다.")
    void testRegister() throws Exception {
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
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("회원정보 수정에 성공하면 /users 로 리다이렉트")
    void updateUserTest() throws Exception {
        String userListUri = "/users";
        String updateUserUri = "/users/cori123";
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUserId", "cori123");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(updateUserUri)
                .param("password", "1111")
                .param("newPassword", "2222")
                .param("name", "cori")
                .param("email", "cori@naver.com")
                .session(session);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(userListUri));
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 회원정보 수정 페이지 유지한다.")
    void failedToUpdateUserTest() throws Exception {
        doThrow(IllegalArgumentException.class).when(userService).update(any());

        String userUpdateFormUri = "/users/cori123/form";
        String updateUserUri = "/users/cori123";
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("loginUserId", "cori123");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(updateUserUri)
                .param("password", "1111")
                .param("newPassword", "2222")
                .param("name", "cori")
                .param("email", "cori@naver.com")
                .session(session);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(userUpdateFormUri));
    }
}