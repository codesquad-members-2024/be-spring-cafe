package codesquad.springcafe.domain.question.controller;

import codesquad.springcafe.domain.comment.data.CommentListResponse;
import codesquad.springcafe.domain.comment.service.CommentService;
import codesquad.springcafe.domain.question.data.QuestionListResponse;
import codesquad.springcafe.domain.question.data.QuestionRequest;
import codesquad.springcafe.domain.question.data.QuestionResponse;
import codesquad.springcafe.domain.question.service.QuestionService;
import codesquad.springcafe.domain.user.data.UserCredentials;
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
    private final CommentService commentService;

    @Autowired
    public QuestionController(QuestionService questionService, CommentService commentService) {
        this.questionService = questionService;
        this.commentService = commentService;
    }

    // 게시글 작성
    @PostMapping("/post")
    public String postQuestion(HttpSession httpSession,
                               @Valid @ModelAttribute QuestionRequest questionRequest,
                               RedirectAttributes redirectAttributes) {
        Long userId = getUserCredentials(httpSession).getUserId();
        Long questionId = questionService.postQuestion(userId, questionRequest);

        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/{questionId}";
    }

    // 게시글 목록 조회
    @GetMapping({"/", "/questions"})
    public String getQuestions(HttpSession httpSession, Model model) {
        Object userId = httpSession.getAttribute("userId");
        QuestionListResponse questionListResponse = questionService.getQuestions(userId);

        model.addAttribute("totalQuestionCnt", questionListResponse.getTotalQuestionCnt());
        model.addAttribute("questions", questionListResponse.getQuestions());

        return "index";
    }

    // 게시글 상세 조회
    @GetMapping("/question/{questionId}")
    public String getQuestion(HttpSession httpSession,
                              @PathVariable("questionId") Long questionId, Model model) {
        Long userId = getUserCredentials(httpSession).getUserId();
        QuestionResponse questionResponse = questionService.getQuestion(userId, questionId);
        CommentListResponse comments = commentService.getComments(userId, questionId);

        model.addAttribute("question", questionResponse);
        model.addAttribute("totalCommentCnt", comments.getTotalCommentCnt());
        model.addAttribute("comments", comments.getComments());

        return "post/show";
    }

    // 게시글 수정 페이지 접근
    @GetMapping("/question/edit/{questionId}")
    public String getQuestionEditForm(HttpSession httpSession,
                                      @PathVariable("questionId") Long questionId,
                                      Model model) {
        Long userId = getUserCredentials(httpSession).getUserId();

        QuestionResponse questionResponse = questionService.getEditQuestion(userId, questionId);
        model.addAttribute("question", questionResponse);

        return "post/edit";
    }

    // 게시글 수정
    @PutMapping("/question/edit/{questionId}")
    public String editQuestion(HttpSession httpSession,
                               @PathVariable("questionId") Long questionId,
                               QuestionRequest questionUpdateRequest,
                               RedirectAttributes redirectAttributes) {
        Long userId = getUserCredentials(httpSession).getUserId();
        questionService.editQuestion(userId, questionId, questionUpdateRequest);

        redirectAttributes.addAttribute("questionId", questionId);
        return "redirect:/question/{questionId}";
    }

    // 게시글 삭제 페이지 접근
    @GetMapping("/question/delete/{questionId}")
    public String getQuestionDeleteForm(HttpSession httpSession,
                                      @RequestHeader("referer") String referer,
                                      @PathVariable("questionId") Long questionId,
                                      Model model) {
        Long userId = getUserCredentials(httpSession).getUserId();
        Long deleteQuestionId = questionService.getDeleteQuestion(userId, questionId);

        model.addAttribute("goBack", referer);
        model.addAttribute("questionId", deleteQuestionId);

        return "post/delete";
    }

    // 게시글 삭제
    @DeleteMapping("/question/delete/{questionId}")
    public String deleteQuestion(HttpSession httpSession,
                                 @PathVariable("questionId") Long questionId) {
        Long userId = getUserCredentials(httpSession).getUserId();
        questionService.deleteQuestion(userId, questionId);

        return "redirect:/questions";
    }

    private UserCredentials getUserCredentials(HttpSession httpSession) {
        Object userCredentials = httpSession.getAttribute("userCredentials");
        if (userCredentials == null) {
            throw new IllegalStateException("인증이 필요한 요청입니다.");
        }
        return (UserCredentials) userCredentials;
    }
}
