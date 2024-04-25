package codesquad.springcafe.service;

import codesquad.springcafe.dto.article.ArticleInfoDTO;
import codesquad.springcafe.dto.article.ArticleUpdateDTO;
import codesquad.springcafe.dto.article.ArticleUploadDTO;
import codesquad.springcafe.dto.reply.ReplyInfoDTO;
import codesquad.springcafe.model.Article;
import codesquad.springcafe.model.Reply;
import codesquad.springcafe.repository.article.ArticleRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private Long totalId = 0L;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.totalId = initTotalId();
    }

    public ArticleInfoDTO upload(ArticleUploadDTO articleUploadDTO) {
        Article newArticle = articleUploadDTO.toArticle(++totalId);
        articleRepository.save(newArticle);
        return newArticle.toDTO();
    }

    public List<ArticleInfoDTO> findAll() {
        List<Article> articles = articleRepository.getAll();
        return articles.stream()
            .map(Article::toDTO)
            .collect(Collectors.toList());
    }

    public ArticleInfoDTO findById(Long id) {
        Optional<Article> targetArticle = articleRepository.getById(id);
        return targetArticle.map(Article::toDTO).orElse(null);
    }

    public List<ReplyInfoDTO> findRepliesById(Long id) {
        List<Reply> replies = articleRepository.getRepliesById(id);
        return replies.stream()
            .map(Reply::toDTO)
            .collect(Collectors.toList());
    }

    public ArticleInfoDTO updateInfo(Long id, ArticleUpdateDTO updateInfo) {
        ArticleInfoDTO originalArticle = findById(id);
        Article modifiedArticle = updateInfo.toArticle(id, originalArticle.getTimestamp(), originalArticle.getWriter());
        articleRepository.modify(modifiedArticle);
        return modifiedArticle.toDTO();
    }

    public void delete(Long id) {
        articleRepository.remove(id);
    }

    private Long initTotalId() {
        return articleRepository.getAll().stream()
            .mapToLong(Article::getId)
            .max().orElse(0L);
    }
}
