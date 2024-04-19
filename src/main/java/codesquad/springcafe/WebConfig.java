package codesquad.springcafe;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.article.ArticleH2Database;
import codesquad.springcafe.database.comment.CommentDatabase;
import codesquad.springcafe.database.comment.CommentH2Database;
import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.database.user.UserH2Database;
import codesquad.springcafe.interceptor.ArticleAccessInterceptor;
import codesquad.springcafe.interceptor.LoginCheckInterceptor;
import codesquad.springcafe.interceptor.UserAccessInterceptor;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 데이터베이스 등 의존성을 주입하는 클래스이다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final int LOGIN_CHECK_ORDER = 1;
    public static final int USER_ACCESS_ORDER = 2;
    public static final int ARTICLE_ACCESS_ORDER = 3;
    private final DataSource dataSource;

    public WebConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(LOGIN_CHECK_ORDER)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/users/add", "/login", "/logout", "/images/**",
                        "/css/**", "/*.ico",
                        "/error");

        registry.addInterceptor(new UserAccessInterceptor())
                .order(USER_ACCESS_ORDER)
                .addPathPatterns("/users/edit/*", "/users/profile/*");

        registry.addInterceptor(new ArticleAccessInterceptor())
                .order(ARTICLE_ACCESS_ORDER)
                .addPathPatterns("/articles/edit/*", "/articles/delete/*");
    }

    /**
     * UserDatabase의 구현체를 설정한다.
     */
    @Bean
    public UserDatabase userDatabase() {
//        return new UserMemoryDatabase();
        return new UserH2Database(dataSource);
    }

    /**
     * ArticleDatabase의 구현체를 설정한다.
     */
    @Bean
    public ArticleDatabase articleDatabase() {
//        return new ArticleMemoryDatabase();
        return new ArticleH2Database(dataSource);
    }

    /**
     * CommentDatabase의 구현체를 설정한다.
     */
    @Bean
    public CommentDatabase commentDatabase() {
        return new CommentH2Database(dataSource);
    }
}
