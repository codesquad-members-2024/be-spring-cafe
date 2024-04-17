package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.RegisterArticle;
import codesquad.springcafe.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository h2ArticleRepository;

    @Autowired
    public ArticleService(ArticleRepository h2ArticleRepository) {
        this.h2ArticleRepository = h2ArticleRepository;
    }

    // 게시글 등록
    public void registerArticle(RegisterArticle registerArticle) {
        Article article = new Article(
                registerArticle.getWriter(), registerArticle.getTitle(),
                registerArticle.getContents(), registerArticle.getTime()
        );
        h2ArticleRepository.add(article);
    }

    // 게시글 상세보기
    // e) 해당하는 게시글이 없으면 에러
    public Article getArticleDetail(String articleId) {
        return h2ArticleRepository.getById(Long.parseLong(articleId));
    }
}
