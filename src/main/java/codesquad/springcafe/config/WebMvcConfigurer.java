package codesquad.springcafe.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

public interface WebMvcConfigurer {
    void addViewControllers(ViewControllerRegistry registry);
}
