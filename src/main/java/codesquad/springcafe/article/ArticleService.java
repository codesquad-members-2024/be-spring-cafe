package codesquad.springcafe.article;

import codesquad.springcafe.article.DTO.ArticlePostReq;
import codesquad.springcafe.article.DTO.ArticleWithComments;
import codesquad.springcafe.article.repository.ArticleRepository;
import codesquad.springcafe.comment.repository.CommentRepository;
import codesquad.springcafe.user.DTO.SimpleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    public void postArticle(ArticlePostReq articlePostReq, SimpleUserInfo author) {
        if (author == null) author = new SimpleUserInfo("guest", "익명");
        articleRepository.add(articlePostReq, author);

    }

    public ArticleWithComments getArticle(int articleId){
        return new ArticleWithComments(
                articleRepository.findById(articleId),
                commentRepository.findByArticleId(articleId));
    }

    public void addPoint(Article article){
        articleRepository.addPoint(article);
    }
}
