package codesquad.springcafe.domain.question.controller;

import codesquad.springcafe.domain.question.data.QuestionPostRequest;
import codesquad.springcafe.domain.question.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String postQuestion(HttpSession httpSession, @Valid @ModelAttribute QuestionPostRequest questionPostRequest) {
        Object attr = httpSession.getAttribute("userId");
        if(attr == null) throw new IllegalStateException("인증이 필요한 요청입니다."); // TODO : exception 추가

        Long userId = (Long) attr;
        questionService.postQuestion(userId, questionPostRequest);

        return "redirect:/";
    }
}
