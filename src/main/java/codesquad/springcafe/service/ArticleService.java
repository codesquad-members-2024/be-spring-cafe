package codesquad.springcafe.service;

import codesquad.springcafe.domain.Article;
import codesquad.springcafe.domain.User;
import codesquad.springcafe.dto.ArticleForm;
import codesquad.springcafe.domain.repository.ArticleRepository;
import codesquad.springcafe.dto.EditArticleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void register(ArticleForm articleForm, String writer) {
        Article article = new Article(
                writer, articleForm.getTitle(),
                articleForm.getContents(), articleForm.getTime()
        );
        articleRepository.add(article);
    }

    // e) 해당하는 게시글이 없으면 에러
    public Article getArticleDetail(String articleId) {
        return articleRepository.getById(Long.parseLong(articleId));
    }

    public void update(String articleId, EditArticleForm editArticleForm) {
        articleRepository.edit(articleId, editArticleForm);
    }

    public void delete(String articleId) {
        articleRepository.delete(articleId);
    }
}