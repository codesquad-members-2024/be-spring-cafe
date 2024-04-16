package codesquad.springcafe.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import codesquad.springcafe.security.PasswordEncryptor;
import codesquad.springcafe.user.database.UserDatabase;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDatabase userDatabase;

    @InjectMocks
    private UserService userService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User("user", "email@dd.com", "nickname", PasswordEncryptor.encrypt("password"));
        user2 = new User("user2", "email2@dd.com", "nickname2",
            PasswordEncryptor.encrypt("password2"));
    }

    @Test
    @DisplayName("유저 정보를 DB에 딱 한 번씩 저장되는지 확인")
    void testSaveUser() {
        userService.save(user1);
        verify(userDatabase).save(user1);
    }

    @Test
    @DisplayName("유저 ")
    void testUpdateUser() {
        User updateInfo = new UserUpdateDto("123", "newNickname", "newEmail").toEntity();
        userService.update(updateInfo, "user");

    }

    @Test
    void findByUserId() {
        when(userDatabase.findByUserId("user")).thenReturn(user1);

        User foundUser = userService.findByUserId("user");

        assertThat(foundUser).isEqualTo(user1);
    }

    @Test
    @DisplayName("모든 유저 정보를 DB에서 가져오는지 확인")
    void testFindAll() {
        UserRequestDto userRequestDto1 = new UserRequestDto(user1.getUserId(), user1.getEmail(),
            user1.getNickname());
        UserRequestDto userRequestDto2 = new UserRequestDto(user2.getUserId(), user2.getEmail(),
            user2.getNickname());
        List<UserRequestDto> users = Arrays.asList(userRequestDto1, userRequestDto2);

        when(userDatabase.findAll()).thenReturn(users);

        List<UserRequestDto> userRequestDtos = userService.findAll();

        assertThat(userRequestDtos).isEqualTo(users);
    }

    @Test
    @DisplayName("프로필을 업데이트할 때 비밀번호가 틀린 경우 예외를 던지는지 테스트")
    void testInvalidPassword() {
        UserUpdateDto userUpdateDto2 = new UserUpdateDto(user2.getPassword(), user2.getEmail(),
            user2.getNickname());

        when(userDatabase.findByUserId("user")).thenReturn(user1);

        assertThatThrownBy(() -> userService.updateUserProfile(userUpdateDto2, "user"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("프로필 수정을 위해 정보를 입력할 때 비밀번호가 맞는경우")
    void testUpdateProfileWithCorrectPassword() {
        UserUpdateDto userUpdateDto = new UserUpdateDto("password",
            user1.getEmail(),
            user1.getNickname());
        when(userDatabase.findByUserId("user")).thenReturn(user1);

        userService.updateUserProfile(userUpdateDto, "user");

    }

}