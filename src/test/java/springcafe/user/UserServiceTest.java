package springcafe.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springcafe.model.User;
import springcafe.repository.UserRepository;
import springcafe.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    public UserServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test1(){

        String id = "A";
        String password = "B";
        String username ="JEONG";
        String email = "dusgh@naver.com";

        User expectedUser = new User(id, password, username, email);
        when(userRepository.findById("A")).thenReturn(expectedUser);

        User resultUser = userService.create(id,password, username, email);


        assertThat(resultUser.getId()).isEqualTo(id);
        assertThat(resultUser.getName()).isEqualTo(username);
        assertThat(resultUser.getEmail()).isEqualTo(email);
        assertThat(resultUser.getId()).isNotEqualTo(password);



    }



}