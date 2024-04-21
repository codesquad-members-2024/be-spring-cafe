package codesquad.springcafe.domain.question.controller;

import codesquad.springcafe.domain.question.data.QuestionListResponse;
import codesquad.springcafe.domain.question.data.QuestionRequest;
import codesquad.springcafe.domain.question.data.QuestionResponse;
import codesquad.springcafe.domain.question.service.QuestionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 게시글 작성
    @PostMapping("/post")
    public String postQuestion(HttpSession httpSession,
                               @Valid @ModelAttribute QuestionRequest questionRequest,
                               RedirectAttributes redirectAttributes) {
        Long userId = getSessionUserId(httpSession);
        Long questionId = questionService.postQuestion(userId, questionRequest);

        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/{questionId}";
    }

    // 게시글 목록 조회
    @GetMapping({"/", "/questions"})
    public String getQuestions(Model model) {
        QuestionListResponse questionListResponse = questionService.getQuestions();

        model.addAttribute("totalQuestionCnt", questionListResponse.getTotalQuestionCnt());
        model.addAttribute("questions", questionListResponse.getQuestions());

        return "index";
    }

    // 게시글 상세 조회
    @GetMapping("/question/{questionId}")
    public String getQuestion(HttpSession httpSession,
                              @PathVariable("questionId") Long questionId, Model model) {
        Long userId = getSessionUserId(httpSession);
        QuestionResponse questionResponse = questionService.getQuestion(userId, questionId);

        model.addAttribute("question", questionResponse);

        return "/post/show";
    }

    // 게시글 수정 페이지 접근
    @GetMapping("/question/{questionId}/edit")
    public String getQuestionEditForm(HttpSession httpSession,
                                      @PathVariable("questionId") Long questionId,
                                      Model model) {
        Long userId = getSessionUserId(httpSession);

        QuestionResponse questionResponse = questionService.getQuestion(userId, questionId);
        model.addAttribute("question", questionResponse);

        return "/post/edit";
    }

    // 게시글 수정
    @PutMapping("/question/{questionId}/edit")
    public String editQuestion(HttpSession httpSession,
                               @PathVariable("questionId") Long questionId,
                               QuestionRequest questionUpdateRequest,
                               RedirectAttributes redirectAttributes) {
        Long userId = getSessionUserId(httpSession);
        questionService.editQuestion(userId, questionId, questionUpdateRequest);

        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/{questionId}";
    }

    private Long getSessionUserId(HttpSession httpSession) {
        Object userId = httpSession.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        return (Long) userId;
    }
}
