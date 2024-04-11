package codesquad.springcafe;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    //addViewControllers 메서드는 일반적으로 간단한 URL 경로를 컨트롤러 클래스나 메서드에 매핑하지 않고,
    // 특정 URL 경로를 바로 뷰로 연결할 때 사용됩니다.
    // 즉, 이 메서드를 사용하여 컨트롤러 클래스나 메서드를 정의하지 않고도 특정 URL 경로로 요청이 들어왔을 때 바로 뷰를 보여줄 수 있습니다.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE); //  ViewControllerRegistry에 등록된 뷰 컨트롤러의 우선순위를 지정합니다.
        //user
        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        //qna
        registry.addViewController("/post/form").setViewName("post/form");
    }
}
