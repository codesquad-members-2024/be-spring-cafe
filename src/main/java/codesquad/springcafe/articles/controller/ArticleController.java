package codesquad.springcafe.articles.controller;


import codesquad.springcafe.articles.model.dto.ArticleUpdateDto;
import codesquad.springcafe.replies.model.dto.ReplyViewDto;
import codesquad.springcafe.articles.service.ArticleService;
import codesquad.springcafe.articles.model.Article;
import codesquad.springcafe.articles.model.dto.ArticleCreationRequest;
import codesquad.springcafe.replies.service.ReplyService;
import codesquad.springcafe.users.model.dto.UserPreviewDto;
import codesquad.springcafe.utils.AuthValidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/articles")
public class ArticleController {

    private static final String SESSION_USER = "sessionedUser";

    private final ArticleService articleService;
    private final ReplyService replyService;
    private final AuthValidateService authValidateService;

    @Autowired
    public ArticleController(ArticleService articleService, ReplyService replyService, AuthValidateService authValidateService) {
        this.articleService = articleService;
        this.replyService = replyService;
        this.authValidateService = authValidateService;
    }

    @PostMapping
    public String postArticle(HttpSession session, ArticleCreationRequest articleCreationRequest) {
        // 게시글 생성 시 권한 확인

        // * [VALIDATE] 로그인이 되어 있는 유저야 한다
        authValidateService.validateSession(session);

        // * [VALIDATE] 세션 작성자와 creationRequest의 작성자가 일치해야 한다
        authValidateService.validateWriterMatch(session, articleCreationRequest.getUserId());

        // * article 생성
        articleService.createArticle(articleCreationRequest);

        return "redirect:/";
    }

    @GetMapping("/{articleId}")
    public String showArticle(@PathVariable long articleId, HttpSession session, Model model) {
        articleService.incrementPageViews(articleId);   // 게시글 조회수 증가

        Article article = articleService.findArticleById(articleId);    // 증가된 조회수가 반영된 Article 객체

        String sessionedUserId = ((UserPreviewDto) session.getAttribute(SESSION_USER)).getUserId(); // 세션의 userId 확인

        ArrayList<ReplyViewDto> replies = replyService.getReplies(sessionedUserId, articleId);    // article에 해당하는 replies를 가져옴

        model.addAttribute("article", article);
        model.addAttribute("totalReplies", replies.size());
        model.addAttribute("replies", replies);

        return "article/show";
    }

    @GetMapping("/update/{articleId}")
    public String showArticleUpdatePage(@PathVariable long articleId, HttpSession session, Model model) {
        Article article = articleService.findArticleById(articleId);    // articleId에 해당하는 Article 객체

        authValidateService.validateArticleAuth(session, article);  // session 값과 article 간의 권한 검증 [ 작성자 검증 ]

        model.addAttribute("article", article);
        return "article/updateForm";
    }

    @PutMapping("/{articleId}")
    public String updateArticle(@PathVariable long articleId, HttpSession session, ArticleUpdateDto articleUpdateDto) {
        Article article = articleService.findArticleById(articleId);    // articleId에 해당하는 Article 객체

        authValidateService.validateArticleAuth(session, article);      // session 값과 article 간의 권한 검증 [ 작성자 검증 ]

        articleService.updateArticle(articleId, articleUpdateDto);      // articleId에 해당하는 article 레코드 수정

        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle(@PathVariable long articleId, HttpSession session, Model model) {
        Article article = articleService.findArticleById(articleId);    // articleId에 해당하는 Article 객체

        authValidateService.validateArticleAuth(session, article);      // session 값과 article 간의 권한 검증 [ 작성자 검증 ]

        articleService.deleteArticle(articleId);

        return "redirect:/";
    }

}
