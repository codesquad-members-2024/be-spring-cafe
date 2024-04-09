package codesquad.springcafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        /* User */
        registry.addViewController("/users/join").setViewName("user/form"); // 유저 회원가입
        registry.addViewController("/users/login").setViewName("user/login");   // 유저 로그인

        /* Article */
        registry.addViewController("/articles/write").setViewName("article/form");  // 게시글 작성
    }
}
