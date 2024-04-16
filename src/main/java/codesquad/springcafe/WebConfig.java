package codesquad.springcafe;

import codesquad.springcafe.database.article.ArticleDatabase;
import codesquad.springcafe.database.article.ArticleH2Database;
import codesquad.springcafe.database.user.UserDatabase;
import codesquad.springcafe.database.user.UserH2Database;
import codesquad.springcafe.interceptor.LoginCheckInterceptor;
import codesquad.springcafe.interceptor.UserProfileInterceptor;
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
    private final DataSource dataSource;

    public WebConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/users/add", "/login", "/logout", "/images/**",
                        "/css/**", "/*.ico",
                        "/error");

        registry.addInterceptor(new UserProfileInterceptor())
                .order(2)
                .addPathPatterns("/users/edit/*", "/users/profile/*");
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
}
