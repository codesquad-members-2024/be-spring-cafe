package codesquad.springcafe.domain.article;

import codesquad.springcafe.domain.article.DTO.ArticlePostReq;
import codesquad.springcafe.domain.article.DTO.ArticleWithComments;
import codesquad.springcafe.domain.article.repository.ArticleRepository;
import codesquad.springcafe.domain.comment.repository.CommentRepository;
import codesquad.springcafe.domain.user.DTO.SimpleUserInfo;
import codesquad.springcafe.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Article article = articleRepository.findById(articleId);

        if (article == null) {
            throw new NotFoundException();
        }

        articleRepository.addPoint(articleId);
        return new ArticleWithComments(article, commentRepository.findByArticleId(articleId));
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
}
