package codesquad.springcafe.config;

import codesquad.springcafe.config.interceptor.AuthorizationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns("/users/**", "/articles/**")
                .excludePathPatterns("/users/login", "/users/create");
    }
}