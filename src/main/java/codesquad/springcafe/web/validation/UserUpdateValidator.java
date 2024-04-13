package codesquad.springcafe.web.validation;

import codesquad.springcafe.web.dto.UserUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserUpdateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserUpdateDto userUpdateDto = (UserUpdateDto) target;

        if (!StringUtils.hasText(userUpdateDto.getCurrentPassword())) {
            errors.rejectValue("currentPassword", "temporary", null, "현재 비밀번호를 입력하세요.");
        }
        if (!StringUtils.hasText(userUpdateDto.getNewPassword())) {
            errors.rejectValue("newPassword", "temporary", null, "새 비밀번호를 입력하세요.");
        }
        if (!StringUtils.hasText(userUpdateDto.getNewPasswordCheck())) {
            errors.rejectValue("newPasswordCheck", "temporary", null, "새 비밀번호 확인을 입력하세요.");
        }
        if (!StringUtils.hasText(userUpdateDto.getName())) {
            errors.rejectValue("name", "temporary", null, "이름을 입력하세요.");
        }
        if (!StringUtils.hasText(userUpdateDto.getEmail())) {
            errors.rejectValue("email", "temporary", null, "이메일을 입력하세요.");
        }
        if (!userUpdateDto.getNewPassword().equals(userUpdateDto.getNewPasswordCheck())) {
            errors.rejectValue("newPasswordCheck", "temporary", null, "비밀번호가 일치하지 않습니다.");
        }
    }
}
