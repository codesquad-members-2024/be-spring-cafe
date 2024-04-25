package codesquad.springcafe;

import codesquad.springcafe.db.article.ArticleDatabase;
import codesquad.springcafe.db.article.H2ArticleDatabase;
import codesquad.springcafe.db.user.H2UserDatabase;
import codesquad.springcafe.db.user.UserDatabase;
import codesquad.springcafe.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final DataSource dataSource;

    public WebConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public UserDatabase userDatabase() {
        return new H2UserDatabase(dataSource);
    }

    @Bean
    public ArticleDatabase articleDatabase() {
        return new H2ArticleDatabase(dataSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/images/**", "/css/**", "/*.ico", "/error" ,
                        "/user/**", "/login", "/users/add", "/articles/detail/**");
    }
}
