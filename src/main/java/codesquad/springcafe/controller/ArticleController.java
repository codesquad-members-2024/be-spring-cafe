package codesquad.springcafe.controller;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.UpdateArticle;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.ArticleCreateDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleDatabase articleDatabase;

    @Autowired
    public ArticleController(@Qualifier("H2ArticleDatabase") ArticleDatabase articleDatabase) {
        this.articleDatabase = articleDatabase;
    }

    @GetMapping("/main")
    public String showArticle(Model model) {
        model.addAttribute("articles", articleDatabase.getAllArticles()); // 전체 article 반환
        model.addAttribute("totalArticleNumber", Integer.toString(articleDatabase.getArticleSize())); // article 개수 반환

        return "main";
    }

    @PostMapping("/article")
    // @ModelAttribute 어노테이션 을 통해 Post body를 파싱해 articleCreateDto 객체로 반환한다.
    public String saveArticle(@ModelAttribute ArticleCreateDto articleCreateDto, HttpServletRequest request) {
        Article newArticle = articleCreateDto.makeArticle(getSessionUser(request).getUserId()); // dto Article로 변환
        articleDatabase.saveArticle(newArticle);
        logger.debug("new article: {}", newArticle.toString());

        // article를 저장 후 main 페이지로 redirect
        return "redirect:/main";
    }

    // 게시글 상세 페이지
    @GetMapping("/article/{id}")
    public String showArticle(@PathVariable int id, Model model) {

        articleDatabase.incrementViewsById(id); // 조회수 1 증가
        Article article = articleDatabase.getArticleById(id);
        model.addAttribute("article", article); // 해당 id에 맞는 article 반환

        return "article/show";
    }

    @GetMapping("/article/update/{userId}/{id}")
    // 게시글 수정 폼 보여주기
    public String showArticleForm(@PathVariable String userId, @PathVariable String id, Model model, HttpServletRequest request) {
        Article article = articleDatabase.getArticleById(Integer.parseInt(id));

        String sessionUserId = getSessionUser(request).getUserId();
        if(!userId.equals(sessionUserId)){ // 수정 권한 없을 때 (= 자신이 작성한 글 아님)
            model.addAttribute("article", article); // 해당 id에 맞는 article 반환
            model.addAttribute("noPermission", true); // 권한 없음
            return "article/show";
        }

        model.addAttribute("article", article);
        return "article/update_form";
    }

    @PutMapping("/article/update/{userId}/{id}")
    // 게시글 수정 폼 받아 게시글 수정하기
    public String updateArticle(@PathVariable String userId, @PathVariable String id, @ModelAttribute UpdateArticle updateArticle, Model model, HttpServletRequest request) {
        String sessionUserId = getSessionUser(request).getUserId();
        if(!userId.equals(sessionUserId)){ // 수정 권한 없을 때 (= 자신이 작성한 글 아님)
            model.addAttribute("noPermission", true); // 권한 없음
            return "redirect:/article/{id}";
        }

        articleDatabase.updateArticle(Integer.parseInt(id), updateArticle);
        logger.debug("article update: article id {}", id);
        return "redirect:/main";
    }

    @DeleteMapping("/article/delete/{userId}/{id}")
    // 게시글 삭제하기
    public String deleteArticle(@PathVariable String userId, @PathVariable String id, Model model, HttpServletRequest request) {
        String sessionUserId = getSessionUser(request).getUserId();
        if(!userId.equals(sessionUserId)){ // 수정 권한 없을 때 (= 자신이 작성한 글 아님)
            model.addAttribute("noPermission", true); // 권한 없음
            return "redirect:/article/{id}";
        }

        articleDatabase.deleteArticle(Integer.parseInt(id));
        logger.debug("article delete: article id {}", id);
        return "redirect:/main";
    }

    // 세션에서 값을 가져오는 메서드
    public User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute("sessionUser"); // 세션에서 "sessionUser"의 값을 가져온다
    }
}
