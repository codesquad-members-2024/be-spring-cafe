package codesquad.springcafe.config;

import codesquad.springcafe.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/index.html").setViewName("/");
        registry.addViewController("/users/form").setViewName("/user/form");
        registry.addViewController("/login").setViewName("/user/login");
        registry.addViewController("/articles/form").setViewName("/article/form");
        registry.addViewController("/users/authenticate").setViewName("/user/authenticate");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/css/**", "/js/**", "/fonts/**", "/images/**")
            .excludePathPatterns("/", "/users/form", "/users", "/users/login", "/login", "/page/**");
    }
}

