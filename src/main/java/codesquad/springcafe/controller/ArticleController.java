
package codesquad.springcafe.controller;

import codesquad.springcafe.dto.Article;
import codesquad.springcafe.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/qna")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 게시글 목록 조회
    @GetMapping("")
    public String showAllArticles(Model model){
        List<Article> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles); // 뷰로 데이터 전달하기 위함
        return "qna/list";
    }

    // 게시글 작성 양식
    @GetMapping("/create")
    public String showArticleForm() {
        return "qna/form";
    }

    // 게시글 작성 처리
    // qna/create 경로로 POST 요청을 받으면 호출
    // 입력받은 Article 객체를 저장하고
    // 저장된 게시글의 ID를 RedirectAttributes 에 추가해 상세 페이지로 리다이렉트
        // RedirectAttribute : 저장된 아티클의 아이디를 리다이렉트 주소로 넘겨!
    @PostMapping("/create") // @ModelAttribute : 폼 => 객체 바인딩
    public String createArticle(@ModelAttribute Article article, RedirectAttributes redirectAttributes) {
        Article savedArticle = articleService.createArticle(article);
        logger.info("article info = {}", article);
        redirectAttributes.addAttribute("id", savedArticle.getId());
        return "redirect:/qna/{id}";
    }


    // 게시글 상세 조회
    // 특정 id를 가진 게시글의 상세 정보를 조회한다.
    // qna/{id} 경로로 GET 요청을 받으면 호출
    // 해당 ID의 게시글이 존재하지 않을 경우 로그 기록 게시글 목록으로 리다이렉트
    // 존재하면 해당 게시글을 모델에 추가하고 qna/show 뷰 반환
     @GetMapping("{id}")
    public String showArticleDetail(@PathVariable("id") Long id, Model model){
        Optional<Article> articleOptional = articleService.findArticleById(id);

        if(!articleOptional.isPresent()){
            logger.error("Article with id {} not found", id);
            return "redirect:/qna/list";
        }

        Article article = articleOptional.get();
        logger.debug("article = {}", article);
        model.addAttribute("article", article);
        return "qna/show";
    }
}