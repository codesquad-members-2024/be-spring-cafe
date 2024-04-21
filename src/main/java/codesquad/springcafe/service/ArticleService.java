package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.dto.ArticleForm;
import codesquad.springcafe.domain.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // 게시글 등록
    public void register(ArticleForm articleForm) {
        Article article = new Article(
                articleForm.getWriter(), articleForm.getTitle(),
                articleForm.getContents(), articleForm.getTime()
        );
        articleRepository.add(article);
    }

    // 게시글 상세보기
    // e) 해당하는 게시글이 없으면 에러
    public Article getArticleDetail(String articleId) {
        return articleRepository.getById(Long.parseLong(articleId));
    }
}
