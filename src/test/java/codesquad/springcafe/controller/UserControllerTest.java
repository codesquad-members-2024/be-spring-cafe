package codesquad.springcafe.controller;

import codesquad.springcafe.WebConfig;
import codesquad.springcafe.db.UserDatabase;
import codesquad.springcafe.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = WebConfig.class))
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDatabase userDatabase;

    @BeforeEach
    void setUp(){
        User user1 = new User();
        user1.setUserId("test1");
        user1.setEmail("test1@email.com");
        user1.setPassword("password1");
        user1.setNickname("nickname1");

        User user2 = new User();
        user2.setUserId("test2");
        user2.setEmail("test2@email.com");
        user2.setPassword("password2");
        user2.setNickname("nickname2");

        List<User> users = Arrays.asList(user1, user2);

        willDoNothing().given(userDatabase).addUser(any(User.class));
        given(userDatabase.findAllUsers()).willReturn(users);
        given(userDatabase.getTotalUserNumber()).willReturn(users.size());
        given(userDatabase.findUserByUserId("test1")).willReturn(Optional.of(user1));
        given(userDatabase.findUserByUserId("test2")).willReturn(Optional.of(user2));
    }


    @DisplayName("회원가입에 성공하면 '/users'로 리다이렉트 된다")
    @Test
    void createUserRedirectionTest() throws Exception {
        String userId = "userId";
        String email = "email@email.com";
        String password = "password";
        String nickname = "nickname";

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users")
                .param("userId", userId)
                .param("email", email)
                .param("nickname", nickname)
                .param("password", password);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }


    @DisplayName("'/users' get 요청을 하면 회원가입한 유저의 정보와 총 회원가입 인원 수가 Model에 담겨 View와 함께 반환된다")
    @Test
    void userListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("totalUserNumber", 2))
                .andExpect(view().name("users/list"));
    }


    @DisplayName("존재하지 않는 userId로 '/users/{userId}' 요청을 보내면 IllegalArgument Exception을 반환한다")
    @Test
    void profileExceptionRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/testUser"))
                .andExpect(status().is4xxClientError());
    }


    @DisplayName("존재하는 userId로 '/users/{userId}' 요청을 보낼 경우 'users/profile' 뷰 템플릿을 반환한다")
    @Test
    void profileRequestSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/test1"))
                .andExpect(status().isOk())
                .andExpect(view().name("users/profile"));
    }
}