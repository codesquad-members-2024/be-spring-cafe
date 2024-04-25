package codesquad.springcafe.config;

import codesquad.springcafe.CheckAuthInterceptor;
import codesquad.springcafe.CheckLoginInterceptor;
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

        //registry.addViewController("/").setViewName("index");
        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/login").setViewName("user/login");
        registry.addViewController("/article/form").setViewName("article/form");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CheckLoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns();

        registry.addInterceptor(new CheckAuthInterceptor())
                .order(2)
                .addPathPatterns("/article/form", "/articles/{articleId:\\d+}/**", "/users");
    }
}
