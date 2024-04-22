package codesquad.springcafe.service.validator;

import codesquad.springcafe.domain.User;
import codesquad.springcafe.exception.InvalidAccessException;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    public void validSameUser(User expected, User actual) {
        if (!expected.getUserId().equals(actual.getUserId())) {
            throw new InvalidAccessException("다른 사용자의 정보에 접근할 수 없습니다.");
        }
    }
}