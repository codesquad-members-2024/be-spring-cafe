package codesquad.springcafe.config;

import codesquad.springcafe.interceptor.UserLoginInterceptor;
import codesquad.springcafe.interceptor.UserPermissionInterceptor;
import codesquad.springcafe.interceptor.UserUpdateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserPermissionInterceptor())
                .addPathPatterns("/users/list", "/users/profile/**", "/users/match-pw/**", "/users/update/**",
                        "/article/**")
                .excludePathPatterns("/static/**");
        registry.addInterceptor(new UserLoginInterceptor());
        registry.addInterceptor(new UserUpdateInterceptor())
                .addPathPatterns("/users/match-pw/**", "/users/update/**");
    }
}
