package codesquad.springcafe.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {
    Article addArticle(Article article); // 게시글 추가
    List<Article> findAll(); // 모든 게시글 조회
    Optional<Article> findByIndex(int number); // 특정 인덱스의 게시글 조회
    int articleSize(); // 저장된 게시글의 수 반환
    void clear(); // 저장소 초기화
}
