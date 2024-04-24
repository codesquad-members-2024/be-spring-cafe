package codesquad.springcafe;

import codesquad.springcafe.interceptor.LoginInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/index",
                "/users/loginForm",
                "/css/**", "/js/**",
                "/*.ico", "/images/**" , "/error");
    }

}
