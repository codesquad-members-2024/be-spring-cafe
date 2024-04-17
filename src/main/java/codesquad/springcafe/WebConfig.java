package codesquad.springcafe;

import codesquad.springcafe.db.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final DataSource dataSource;

    public WebConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Bean
    public UserDatabase userDatabase() {
        return new H2UserDatabase(dataSource);
    }

    @Bean
    public ArticleDatabase articleDatabase() {
        return new H2ArticleDatabase(dataSource);
    }
}
