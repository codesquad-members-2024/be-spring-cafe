package codesquad.springcafe.web.validation;

import codesquad.springcafe.web.dto.UserCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // clazz의 타입 확인
        return UserCreateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCreateDto userCreateDto = (UserCreateDto) target;

        if (!StringUtils.hasText(userCreateDto.getUserId())) {
            errors.rejectValue("userId", "temporary", null, "아이디를 입력하세요.");
        }
        if (!StringUtils.hasText(userCreateDto.getPassword())) {
            errors.rejectValue("password", "temporary", null, "비밀번호를 입력하세요.");
        }
        if (!StringUtils.hasText(userCreateDto.getPasswordCheck())) {
            errors.rejectValue("passwordCheck", "temporary", null, "비밀번호 확인을 입력하세요.");
        }
        if (!userCreateDto.getPassword().equals(userCreateDto.getPasswordCheck())) {
            errors.rejectValue("passwordCheck", "temporary", null, "비밀번호가 일치하지 않습니다.");
        }
        if (!StringUtils.hasText(userCreateDto.getName())) {
            errors.rejectValue("name", "temporary", null, "사용자 이름을 입력하세요.");
        }
        if (!StringUtils.hasText(userCreateDto.getEmail())) {
            errors.rejectValue("email", "temporary", null, "이메일을 입력하세요.");
        }
    }
}
