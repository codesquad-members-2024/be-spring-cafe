package codesquad.springcafe.global.config;

import codesquad.springcafe.global.security.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    // 특정 URL 경로에 대한 요청을 처리하는 컨트롤러를 등록
    // 실제 작업을 수행하는 컨트롤러가 아닌 단순히 특정 뷰로 이동시키는 역할 (라우팅 x)
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE); // 다른 빈들보다 우선순위 위로

        registry.addViewController("/").setViewName("/index");  // 기본 경로로 접속 시 templates/index.html 뷰를 반환
        registry.addViewController("/user/login").setViewName("/user/login");
        registry.addViewController("/user/join").setViewName("/user/form");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
