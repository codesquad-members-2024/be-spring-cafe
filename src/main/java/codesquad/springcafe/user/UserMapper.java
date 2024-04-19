package codesquad.springcafe.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserViewDto toListDto(User user, Long id) {
        return new UserViewDto(id, user.getUserId(), user.getNickName(), user.getEmail());
    }

    public UserViewDto toProfileDto(User user) {
        return new UserViewDto(user.getNickName(), user.getEmail());
    }

    public User toCreateUser(UserCreationDto dto) {
        final String userId = dto.getUserId();
        final String password = dto.getPassword();
        final String name = dto.getName();
        final String email = dto.getEmail();

        return new User(userId, password, name, email);
    }

    public User toUpdateUser(String userId, UserCreationDto dto) {
        final String password = dto.getPassword();
        final String name = dto.getName();
        final String email = dto.getEmail();

        return new User(userId, password, name, email);
    }
}
