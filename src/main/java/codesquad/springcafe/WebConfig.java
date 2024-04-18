package codesquad.springcafe;

import codesquad.springcafe.user.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/user/login", "/user/form", "/user/create", "/css/**",
                "/js/**", "/img/**", "/*.ico", "/fonts/**");

    }


}
