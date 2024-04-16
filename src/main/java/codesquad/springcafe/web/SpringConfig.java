//package codesquad.springcafe.web;
//
//import codesquad.springcafe.web.repository.ArticleRepository;
//import codesquad.springcafe.web.repository.MemoryArticleRepository;
//import codesquad.springcafe.web.repository.MemoryUserRepository;
//import codesquad.springcafe.web.repository.UserRepository;
//import codesquad.springcafe.web.service.UserService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SpringConfig {
//    @Bean
//    public UserService userService() {
//        return new UserService(userRepository());
//    }
//
//    @Bean
//    public UserRepository userRepository() {
//        return new MemoryUserRepository();
//    }
//
//    @Bean
//    public ArticleRepository articleRepository() {
//        return new MemoryArticleRepository();
//    }
//}
