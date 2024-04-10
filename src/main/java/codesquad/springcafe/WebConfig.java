package codesquad.springcafe;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.article.ArticleH2Database;
import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.database.user.UserMemoryDatabase;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 데이터베이스 등 의존성을 주입하는 클래스이다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final DataSource dataSource;

    public WebConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * UserDatabase의 구현체를 설정한다.
     */
    @Bean
    public UserDatabase userDatabase() {
        return new UserMemoryDatabase();
    }

    /**
     * ArticleDatabase의 구현체를 설정한다.
     */
    @Bean
    public ArticleDatabase articleDatabase() {
//        return new UserMemoryDatabase();
        return new ArticleH2Database(dataSource);
    }
}
