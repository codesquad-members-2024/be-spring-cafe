package codesquad.springcafe;

import codesquad.springcafe.user.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggerInterceptor())


        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/user/*")
                .excludePathPatterns("/user/log*") // 로그인, 로그아웃 제외
                .excludePathPatterns("/user/form*")
                .excludePathPatterns("/css/**", "/images/**", "/js/**");
    }
}
