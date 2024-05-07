package codesquad.springcafe;

import codesquad.springcafe.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 데이터베이스 등 의존성을 주입하는 클래스이다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    public static final int LOGIN_CHECK_ORDER = 1;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(LOGIN_CHECK_ORDER)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/search", "/users/add", "/login", "/logout", "/images/**",
                        "/css/**", "/*.ico",
                        "/error");
    }
}
