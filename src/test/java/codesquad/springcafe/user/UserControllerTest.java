package codesquad.springcafe.user;

import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.domain.user.UserController;
import codesquad.springcafe.domain.user.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({MockitoExtension.class})
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @DisplayName("회원가입 후 유저 리스트 페이지로 리다이렉트")
    @Test
    void createUser() throws Exception {
        // given
        User user = new User(id, password, name, email);

        // when
        // then
        mockMvc.perform(post("/user")
                        .param("userId", id)
                        .param("password", password)
                        .param("name", name)
                        .param("email", email)
                )
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/users"));

        verify(userService, times(1)).create(any(User.class));
    }

    @DisplayName("유저 프로필 페이지를 조회 가능하다")
    @Test
    void userProfile() throws Exception {
        // given
        User user = new User(id, password, name, email);
        given(userService.getUser(id)).willReturn(user);

        // when
        // then
        mockMvc.perform(get("/user/" + id)
                        .sessionAttr("loginUser", new SimpleUserInfo(id, name)))
                .andExpect(status().is(200))
                .andExpect(model().attribute("user", user));


        verify(userService, times(1)).getUser(id);
    }

    @DisplayName("로그인 하지 않으면 유저 프로필 페이지를 조회할 수 없다")
    @Test
    void userProfileNotLoggedIn() throws Exception {
        // given
        User user = new User(id, password, name, email);
        given(userService.getUser(id)).willReturn(user);

        // when
        // then
        mockMvc.perform(get("/user/" + id))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/user/login"));


        verify(userService, times(0)).getUser(id);
    }

    @DisplayName("로그인하면 세션에 유저 정보를 추가하고 , navBar를 수정한 뒤, 홈으로 리다이렉트")
    @Test
    void login() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        given(userService.login(id, password)).willReturn(new SimpleUserInfo(id, name));

        // when
        // then
        mockMvc.perform(post("/user/login")
                        .session(session)
                        .param("userId", id)
                        .param("password", password))
                .andExpect(status().is(302)).andExpect(redirectedUrl("/"));

        verify(userService, times(1)).login(id, password);
        assertThat(session.getAttribute("loginUser")).isNotNull().isInstanceOf(SimpleUserInfo.class);
    }

    @DisplayName("로그인에 실패하면 로그인 실패 메시지를 보여준다")
    @Test
    void loginFail() throws Exception {
        // given
        MockHttpSession session = new MockHttpSession();
        given(userService.login(id, password)).willReturn(null);

        // when
        // then
        mockMvc.perform(post("/user/login")
                        .session(session)
                        .param("userId", id)
                        .param("password", password))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.")));

        verify(userService, times(1)).login(id, password);
    }






    // test data
    String id = "tester";
    String password = "1234";
    String name = "테스터";
    String email = "test@naver.com";
}
