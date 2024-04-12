package codesquad.springcafe.MemberTest;

import codesquad.springcafe.Member.Member;
import codesquad.springcafe.Member.MemberController;
import codesquad.springcafe.Member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 등록 후 /users로 리다이렉트한다")
    void registerTest() throws Exception {
        // MockMvc를 사용하여 /user에 POST 요청을 보내고, 리다이렉션을 확인합니다.
        mockMvc.perform(post("/user")
                        .param("userId", "test_user")
                        .param("password", "test")
                        .param("name", "테스트 유저")
                        .param("email", "test@test  "))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"));
    }

    @Test
    @DisplayName("/users 요청 시 user/list 뷰로 이동")
    void showListTest() throws Exception {
        // MockMvc를 사용하여 /users에 GET 요청을 보내고, 뷰 이름을 확인합니다.
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    @DisplayName("/users/{userId} 요청 시 user/profile 뷰로 이동")
    void showProfileTest() throws Exception {

        String memberId = "test_user";
        given(memberRepository.getMember(memberId)).willReturn(Optional.of(new Member("test_user", "테스트 유저", "test@test", "test")));

        // MockMvc를 사용하여 /users/{userId}에 GET 요청을 보내고, 뷰 이름을 확인합니다.
        mockMvc.perform(get("/users/{userId}", memberId))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"));
    }
}
