package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.data.UserCredentials;
import codesquad.springcafe.domain.user.data.UserJoinRequest;
import codesquad.springcafe.domain.user.data.UserListResponse;
import codesquad.springcafe.domain.user.data.UserResponse;
import codesquad.springcafe.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// UserController 단위테스트
// TODO: 실패 테스트도 작성

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource(value = {
            "hong,홍길동,hong@gmail.com,1234",
            "lim,임꺽정,lim@gmail.com,5678",
            "shin,신사임당,shin@gmail.com,9012"
    })
    @DisplayName("회원가입에 성공한다.")
    void testPostUser(String loginId, String name, String email, String password) throws Exception {
        //given
        final String url = "/user";

        given(userService.join(new UserJoinRequest(loginId, email, name, password)))
                .willReturn(new UserCredentials(1L, name));

        //when
        final ResultActions result = mockMvc.perform(
                post(url).contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userId", loginId)
                        .param("email", email)
                        .param("name", name)
                        .param("password", password)); // POST

        //then
        result.andExpect(status().isFound())
                .andExpect(redirectedUrl("/welcome"));
    }

    @Test
    @DisplayName("유저 목록 조회에 성공한다.")
    void testGetJoinedUserList() throws Exception {
        //given
        final String url = "/users";

        List<UserResponse> userList = Arrays.asList(
                new UserResponse("hong", "홍길동", "hong@gmail.com", "2024-01-14 13:38:00"),
                new UserResponse("lim","임꺽정", "lim@gmail.com", "2024-01-14 14:38:00"),
                new UserResponse("shin","신사임당", "shin@gmail.com", "2024-01-14 15:38:00")
        );

        given(userService.getUsers()).willReturn(new UserListResponse(userList));

        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("userCredentials", new UserCredentials(1L, "홍길동"));

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .session(httpSession)); // GET

        //then
        result.andExpect(status().isOk())   // 200 OK
                .andExpect(view().name("user/list"))    // 뷰 이름
                .andExpect(model().attribute("totalUserCnt", userList.size()))
                .andExpect(model().attribute("users", containsInAnyOrder(
                        userList.stream().map(user ->
                                allOf(
                                        hasProperty("loginId", equalTo(user.getLoginId())),
                                        hasProperty("name", equalTo(user.getName())),
                                        hasProperty("email", equalTo(user.getEmail())),
                                        hasProperty("createdAt", equalTo(user.getCreatedAt()))
                                )
                        ).collect(Collectors.toList())
                )));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "hong,홍길동,hong@gmail.com,2024-01-14 13:38:00",
            "lim,임꺽정,lim@gmail.com,2024-01-14 14:38:00",
            "shin,신사임당,shin@gmail.com,2024-01-14 15:38:00"
    })
    @DisplayName("유저 프로필 조회에 성공한다.")
    void testGetUserProfile(String loginId, String name, String email, String createdAt) throws Exception {
        //given
        final String url = "/profile/{loginId}";

        given(userService.getUser(loginId)).willReturn(new UserResponse(loginId, email, name, createdAt));

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userCredentials", new UserCredentials(1L, "홍길동"));

        //when
        final ResultActions result = mockMvc.perform(get(url, loginId).session(session));

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attribute("user", allOf(
                        hasProperty("email", equalTo(email)),
                        hasProperty("name", equalTo(name)),
                        hasProperty("createdAt", equalTo(createdAt))
                )));
    }
}