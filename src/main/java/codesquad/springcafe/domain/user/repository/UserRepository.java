package codesquad.springcafe.domain.user.repository;

import codesquad.springcafe.domain.user.User;
import codesquad.springcafe.domain.user.DTO.UserListRes;

import java.util.List;

public interface UserRepository {

    void addUser(User user) throws IllegalArgumentException;

    void update(User user);
    User findUserById(String id);
    List<UserListRes> findAll();
}
