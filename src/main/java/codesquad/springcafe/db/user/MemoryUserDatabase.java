package codesquad.springcafe.db.user;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserCredentialDto;
import codesquad.springcafe.model.user.dto.UserProfileDto;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MemoryUserDatabase implements UserDatabase {
    private final List<User> userDatabase = new ArrayList<>();

    public void addUser(User user) {
        userDatabase.add(user);
    }

    @Override
    public void update(String userId, UserProfileEditDto userProfileEditDto) {
        IntStream.range(0, userDatabase.size())
                .filter(i -> userDatabase.get(i).getUserId().equals(userId))
                .findFirst()
                .ifPresent(i -> userDatabase.set(i, userProfileEditDto.toEntity()));
    }

    public List<UserProfileDto> getAllUsers(){
        return userDatabase.stream()
                .map(user -> new UserProfileDto(
                user.getUserId(), user.getNickname(), user.getEmail(), user.getRegisterTime())
                ).toList();
    }

    public Optional<UserProfileDto> findUserByUserId(String userId){
        return userDatabase.stream()
                .filter(user -> user.getUserId().equals(userId))
                .map(user -> new UserProfileDto(userId, user.getNickname(), user.getEmail(), user.getRegisterTime()))
                .findFirst();
    }

    @Override
    public Optional<UserCredentialDto> getUserCredential(String userId) {
        return userDatabase.stream()
                .filter(user -> user.getUserId().equals(userId))
                .map(user -> new UserCredentialDto(userId, user.getPassword()))
                .findAny();
    }

    public void clearDatabase(){
        userDatabase.clear();
    }

    public int getTotalUserNumber(){
        return userDatabase.size();
    }

    @Override
    public boolean existsByUserId(String userId) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return false;
    }
}
