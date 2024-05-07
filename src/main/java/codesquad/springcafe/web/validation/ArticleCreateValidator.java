package codesquad.springcafe.web.validation;

import codesquad.springcafe.web.dto.ArticleCreateDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ArticleCreateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleCreateDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArticleCreateDto articleCreateDto = (ArticleCreateDto) target;

        if (!StringUtils.hasText(articleCreateDto.getTitle())) {
            errors.rejectValue("title", "temporary", null, "제목을 입력하세요.");
        }
        if (!StringUtils.hasText(articleCreateDto.getContents())) {
            errors.rejectValue("contents", "temporary", null, "내용을 입력하세요.");
        }
    }
}
