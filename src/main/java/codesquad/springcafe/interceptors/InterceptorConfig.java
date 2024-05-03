package codesquad.springcafe.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/user/**", "/article/**", "/comment/**")
                .excludePathPatterns("/user/*.html");

        registry.addInterceptor(new CommonModelInjector())
                .addPathPatterns("/user/**", "/article/**", "/comment/**", "/**")
                .excludePathPatterns("/user/*.html");
    }
}
