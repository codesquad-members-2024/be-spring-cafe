package codesquad.springcafe;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        //registry.addViewController("/main").setViewName("main");
        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/article/form").setViewName("article/form");
        registry.addViewController("/article/show").setViewName("article/show");
    }
}
