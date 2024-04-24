package springcafe.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import springcafe.interceptor.AuthCheckInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor())
                .addPathPatterns("/user/update"
                        ,"/user/list"
                        ,"/qna/questions"
                        ,"/user/*/form"
                        ,"/user/profile/*"
                        ,"/qna/show/*"
                        ,"/reply/create/*"
                        ,"/reply/update/*"
                        ,"/reply/delete/*");
    }


    @Bean
    public AuthCheckInterceptor authCheckInterceptor(){
        return new AuthCheckInterceptor();
    }
}
