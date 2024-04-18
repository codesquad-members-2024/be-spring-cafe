package springcafe.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import springcafe.user.model.User;
import springcafe.user.repository.UserDao;
import springcafe.user.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserDao userDao;

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
        when(userDao.findById("A")).thenReturn(expectedUser);

        User resultUser = userService.create(id,password, username, email);


        assertThat(resultUser.getUserId()).isEqualTo(id);
        assertThat(resultUser.getName()).isEqualTo(username);
        assertThat(resultUser.getEmail()).isEqualTo(email);
        assertThat(resultUser.getUserId()).isNotEqualTo(password);



    }



}