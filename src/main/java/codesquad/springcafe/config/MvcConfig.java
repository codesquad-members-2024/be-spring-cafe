package codesquad.springcafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /* Main Redirect */
        registry.addRedirectViewController("/", "main");    // URL에 /을 입력하면 항상 /main 으로 접속된다

        /* User */
        registry.addViewController("/users/join").setViewName("user/form"); // 유저 회원가입

        /* Article */
        registry.addViewController("/articles/write").setViewName("article/form");  // 게시글 작성

        /* 우선순위를 가장 높게 설정 */
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
