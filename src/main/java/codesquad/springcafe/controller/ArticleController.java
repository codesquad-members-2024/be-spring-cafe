package codesquad.springcafe.controller;

import codesquad.springcafe.form.article.ArticleWriteForm;
import codesquad.springcafe.form.comment.CommentWriteForm;
import codesquad.springcafe.form.user.LoginUser;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Comment;
import codesquad.springcafe.model.UploadFile;
import codesquad.springcafe.service.ArticleService;
import codesquad.springcafe.service.CommentService;
import codesquad.springcafe.service.FileStoreService;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    public static final long FIRST_OFFSET = 0L;
    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final CommentService commentService;
    private final FileStoreService fileStoreService;

    public ArticleController(ArticleService articleService, CommentService commentService,
                             FileStoreService fileStoreService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.fileStoreService = fileStoreService;
    }

    /**
     * 사용자에게 아티클 폼을 보여줍니다.
     */
    @GetMapping("/add")
    public String articleForm(@ModelAttribute("articleWriteForm") ArticleWriteForm articleWriteForm) {
        return "article/form";
    }

    /**
     * 사용자가 작성한 아티클을 생성하고 데이터베이스에 저장합니다.
     */
    @PostMapping("/add")
    public String addArticle(@Validated @ModelAttribute ArticleWriteForm articleWriteForm, BindingResult bindingResult,
                             @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser)
            throws IOException {
        if (bindingResult.hasErrors()) {
            logger.error("errors ={}", bindingResult);
            return "article/form";
        }

        Article article = articleService.writeArticle(articleWriteForm, loginUser.getNickname());// 게시글 업데이트
        fileStoreService.storeFile(article.getId(), articleWriteForm.getFile()); // 파일 업데이트

        return "redirect:/";
    }

    /**
     * 사용자가 요청한 id의 아티클을 조회수를 올리고 렌더링하여 보여줍니다. 일치하는 id가 데이터베이스에 존재하지 않는다면 홈으로 리다이렉트합니다.
     */
    @GetMapping("/detail/{id}")
    public String viewArticle(@PathVariable Long id, Model model,
                              @ModelAttribute("commentWriteForm") CommentWriteForm commentWriteForm) {

        Article article = articleService.viewArticle(id);
        List<Comment> comments = commentService.getCommentsByOffset(id, FIRST_OFFSET);
        Long commentCount = commentService.getCommentCount(article);
        List<UploadFile> uploadFiles = fileStoreService.findFilesByArticleId(id);

        model.addAttribute("article", article);
        model.addAttribute("comments", comments);
        model.addAttribute("commentCount", commentCount);
        model.addAttribute("uploadFiles", uploadFiles);

        return "article/show";
    }

    /**
     * id와 일치하는 게시글을 찾고 수정 폼을 보여줍니다.
     */
    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable Long id, Model model,
                             @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser) {
        articleService.findArticle(id);
        articleService.validateAccess(id, loginUser.getNickname());
        ArticleWriteForm articleUpdateForm = articleService.getArticleUpdateForm(id);
        model.addAttribute("articleWriteForm", articleUpdateForm);
        return "article/update";
    }

    /**
     * id와 일치하는 게시글을 찾고 업데이트합니다.
     */
    @PutMapping("/edit/{id}")
    public String updateArticle(@PathVariable Long id, @Validated @ModelAttribute ArticleWriteForm articleWriteForm,
                                BindingResult bindingResult,
                                @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser) {
        if (bindingResult.hasErrors()) {
            return "article/update";
        }
        articleService.updateArticle(id, articleWriteForm, loginUser.getNickname());

        return "redirect:/articles/detail/" + id;
    }

    /**
     * id와 일치하는 게시글을 찾아 삭제 폼을 보여줍니다.
     */
    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id, Model model,
                             @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser) {
        articleService.findArticle(id);
        articleService.validateAccess(id, loginUser.getNickname());
        articleService.validateOtherComment(id, loginUser.getNickname());
        model.addAttribute("articleId", id);
        return "article/delete";
    }

    /**
     * 게시물의 작성자와 다른 유저가 작성한 코멘트가 존재할 경우 게시물을 삭제할 수 없습니다.
     */
    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id,
                                @SessionAttribute(LoginController.LOGIN_SESSION_NAME) LoginUser loginUser) {
        articleService.deleteArticle(id, loginUser.getNickname());
        return "redirect:/";
    }
}
