//package codesquad.springcafe.web.repository;
//
//import codesquad.springcafe.web.domain.Article;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//
//public class MemoryArticleRepository implements ArticleRepository{
//    private static List<Article> articles = new ArrayList<>();
//
//    @Override
//    public Article save(Article article) {
//        articles.add(article);
//        return article;
//    }
//
//    @Override
//    public List<Article> articlesAll() {
//        return articles;
//    }
//
////    @Override
////    public int articleSize() {
////        return articles.size();
////    }
//
//    @Override
//    public Optional<Article> findByIndex(int number) {
//        int index = number - 1;
//        if (index >= 0 && index < articles.size()) {
//            return Optional.of(articles.get(index));
//        } else {
//            return Optional.empty();
//        }
//
//    }
//}
