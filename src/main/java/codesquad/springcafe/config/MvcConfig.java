package codesquad.springcafe.config;

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
        registry.addViewController("qna/form").setViewName("qna/form");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CheckLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns();
    }
}
