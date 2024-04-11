package codesquad.springcafe.domain.user.controller;

import codesquad.springcafe.domain.user.model.User;
import codesquad.springcafe.domain.user.model.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("유저 목록 조회에 성공한다.")
    void getJoinedUserList() throws Exception {
        //given
        final String url = "/users";
        List<User> userList = Arrays.asList(
                new User("홍길동", "hong@gmail.com", "1234", LocalDateTime.now(), LocalDateTime.now()),
                new User("임꺽정", "lim@gmail.com", "5678", LocalDateTime.now(), LocalDateTime.now()),
                new User("신사임당", "shin@gmail.com", "9012", LocalDateTime.now(), LocalDateTime.now())
        );
        userList.forEach(user -> userRepository.save(user));

        //when
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.TEXT_HTML)); // GET

        //then
        result.andExpect(status().isOk())   // 200 OK
                .andExpect(content().contentType(MediaType.TEXT_HTML))  // HTML 응답
                .andExpect(view().name("user/list"))    // 뷰 이름
                .andExpect(model().attribute("totalUserCnt", userList.size()))
                .andExpect(model().attribute("users", containsInAnyOrder(
                        userList.stream().map(user ->
                                allOf(
                                        hasProperty("id", equalTo(user.getId())),
                                        hasProperty("name", equalTo(user.getName())),
                                        hasProperty("email", equalTo(user.getEmail()))
                                )
                        ).collect(Collectors.toList())
                )));
    }
}