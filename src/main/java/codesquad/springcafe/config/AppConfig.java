package codesquad.springcafe.config;

import codesquad.springcafe.interceptor.ArticlePermissionInterceptor;
import codesquad.springcafe.interceptor.ArticleShowInterceptor;
import codesquad.springcafe.interceptor.UserPermissionInterceptor;
import codesquad.springcafe.interceptor.UserUpdateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserPermissionInterceptor()).order(1)
                .addPathPatterns("/users/list", "/users/profile/**", "/users/match-pw/**", "/users/update/**",
                        "/article/**")
                .excludePathPatterns("/static/**");
        registry.addInterceptor(new UserUpdateInterceptor()).order(2)
                .addPathPatterns("/users/match-pw/**", "/users/update/**");
        registry.addInterceptor(new ArticleShowInterceptor()).order(3)
                .addPathPatterns("/article/show/**");
        registry.addInterceptor(new ArticlePermissionInterceptor()).order(4)
                .addPathPatterns("/article/update/**");
    }
}
