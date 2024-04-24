package codesquad.springcafe.domain.article;

import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.article.DTO.ArticleWithComments;
import codesquad.springcafe.domain.article.repository.ArticleRepository;
import codesquad.springcafe.domain.comment.CommentService;
import codesquad.springcafe.domain.comment.DTO.Comment;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import codesquad.springcafe.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    @Autowired
    public ArticleService(ArticleRepository articleRepository, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.commentService = commentService;
    }

    public void postArticle(ArticlePostReq articlePostReq, SimpleUserInfo author) {
        if (author == null) author = new SimpleUserInfo("guest", "익명");
        articleRepository.add(articlePostReq, author);

    }

    public ArticleWithComments getArticle(int articleId){
        Article article = articleRepository.findById(articleId);

        if (article == null) {
            throw new NotFoundException();
        }

        articleRepository.addPoint(articleId);
        return new ArticleWithComments(article, commentService.findByArticleId(articleId, 1));
    }

    public List<Article> getArticlesAtPage(int page){
        return articleRepository.getArticles(page);
    }

    public List<Article> findByUserId(String id) {
        return articleRepository.findByUserId(id);
    }

    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public void modify(int id, ArticlePostReq articlePostReq) {
        articleRepository.update(id, articlePostReq);
    }

    public void delete(int id){
        articleRepository.delete(id);
    }

    public boolean canModify(int id, SimpleUserInfo loginUser) {
        if(loginUser == null) return false;
        return articleRepository.findById(id).getAuthor().id().equals(loginUser.id());
    }

    public boolean canDelete(int id, SimpleUserInfo loginUser) {
        if (!canModify(id , loginUser)) return false;

        List<Comment> byArticleId = commentService.findByArticleId(id);
        for (Comment comment : byArticleId){
            if (!commentService.canModify(comment.getId(), loginUser)) return false;
        }

        return true;
    }
}
