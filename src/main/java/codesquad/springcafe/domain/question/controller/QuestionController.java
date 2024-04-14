package codesquad.springcafe.domain.question.controller;

import codesquad.springcafe.domain.question.data.QuestionListResponse;
import codesquad.springcafe.domain.question.data.QuestionPostRequest;
import codesquad.springcafe.domain.question.data.QuestionResponse;
import codesquad.springcafe.domain.question.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/post")
    public String postQuestion(HttpSession httpSession, @Valid @ModelAttribute QuestionPostRequest questionPostRequest) {
        Object attr = httpSession.getAttribute("userId");
        if(attr == null) throw new IllegalStateException("인증이 필요한 요청입니다."); // TODO : exception 추가

        Long userId = (Long) attr;
        questionService.postQuestion(userId, questionPostRequest);

        return "redirect:/questions";
    }

    @GetMapping({"/", "/questions"})
    public String getQuestions(Model model) {
        QuestionListResponse questionListResponse = questionService.getQuestions();

        model.addAttribute("totalQuestionCnt", questionListResponse.getTotalQuestionCnt());
        model.addAttribute("questions", questionListResponse.getQuestions());

        return "index";
    }

    @GetMapping("/question/{questionId}")
    public String getQuestion(HttpSession httpSession,
                              @PathVariable("questionId") Long questionId, Model model) {
        Object userId = httpSession.getAttribute("userId");
        // 세션에 userId 값이 없으면 권한 없음 예외
        if (userId == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        QuestionResponse questionResponse = questionService.getQuestion((Long) userId, questionId);

        model.addAttribute("question", questionResponse);

        return "/post/show";
    }
}
