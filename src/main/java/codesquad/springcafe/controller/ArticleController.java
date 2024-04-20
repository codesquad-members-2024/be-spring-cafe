package codesquad.springcafe.controller;

import codesquad.springcafe.model.Article;
import codesquad.springcafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    ArticleService articleService = new ArticleService();

    /**
     * 글 쓰기 화면
     *
     * @GetMapping("qna/create") 어노테이션
     * 요청 경로가 /qna/create일 때 이 메소드가 실행됨을 나타낸다.
     * 사용자가 글을 작성하기 위해 접속하는 화면을 보여주기 위한 것
     * 스프링 MVC는 요청을 받으면 해당 메소드를 실행하고,
     * 이 메소드의 반환 값인 qna/form을 뷰 리졸버(View Resolver)에게 전달한다.
     * 뷰 리졸버는 qna/form을 기반으로 실제 화면을 생성하여 사용자에게 전달한다.
     */
    @GetMapping("qna/create")
    public String quest() {
        return "qna/form"; // 해당 템플릿을 기반으로 View 를 만들어준다. -> ModelandView
    }

    /**
     * 글 쓰기
     *
     * @PostMapping("qna/create") 어노테이션
     * POST 방식의 요청이 /qna/create 경로로 들어오면 이 메소드가 실행된다.
     * 이 메소드는 사용자가 작성한 글을 실제로 저장하기 위한 것
     * @ModelAttribute Article article은 요청으로부터 전송된 데이터를 자동으로 Article 객체에 바인딩한다.
     * articleService.writeArticle(article)를 호출하여 해당 글을 저장하고, 그 결과로 저장된 글의 ID를 반환받을 수 있다.
     * 마지막으로는 redirect:/를 반환하여 사용자를 홈페이지로 리다이렉트 한다.
     */

    // 사용자가 입력한 데이터를 받아서 Article 객체로 변환한 후, 이를 사용하여 새로운 글을 저장한다.
    @PostMapping("qna/create")
    public String quest(@ModelAttribute Article article) {
        articleService.writeArticle(article);
        return "redirect:/"; // url
    }

    /**
     * 글 목록 조회하기
     *
     * @GetMapping("/") 어노테이션은 루트 경로(/)로 GET 요청이 들어오면 이 메소드가 실행된다.
     * 사용자가 처음 사이트에 접속했을 때 보여줄 홈페이지를 구성한다.
     * model.addAttribute("articles", articleService.findArticles())는
     * articleService를 통해 모든 글을 조회하고, 조회된 글 목록을 articles라는 이름으로 모델에 추가한다.
     * 그 후에는 index를 반환하여 홈페이지 템플릿을 렌더링하고, 사용자에게 보여준다.
     */

    // model.addAttribute("articles", articleService.findArticles())는 조회된 글 목록을 articles 라는 이름으로 모델에 추가
    // 이렇게 함으로써 뷰(템플릿)에서 이 데이터에 접근할 수 있게 된다.
    // 즉, 이제 화면에서 articles 라는 이름으로 글 목록을 사용할 수 있게 되는 것
    @GetMapping("/")
    public String articleList(Model model) {
        model.addAttribute("articles", articleService.findArticles());
        return "index";
    }


    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        model.addAttribute("article", articleService.findArticleById(id)); // 객체의 이름을 지정하고 타임리프 통해서 html 사용할 수 있게 해준다.
        return "qna/show";
    }
}
