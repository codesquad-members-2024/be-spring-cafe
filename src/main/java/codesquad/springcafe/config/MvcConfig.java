package codesquad.springcafe.config;

import codesquad.springcafe.config.interceptor.LoggedInInterceptor;
import codesquad.springcafe.config.interceptor.LoggedOutInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 로그인 된 상태에서 접속하지 못하는 경로 설정
        registry.addInterceptor(new LoggedInInterceptor())
                .addPathPatterns("/users/login")    // 로그인 페이지
                .addPathPatterns("/users/join");    // 회원가입 페이지

        // 로그인 하지 않은 상태에서 접속하지 못하는 경로 설정
        registry.addInterceptor(new LoggedOutInterceptor())
                .addPathPatterns("/users/profile/{userId}") // 유저 프로필 보기
                .addPathPatterns("/articles/write")         // 게시글 작성
                .addPathPatterns("/articles/{articleId}")   // 게시글 세부 내용
                .addPathPatterns("/articles/update/{articleId}");   // 게시글 수정 페이지

    }
}
