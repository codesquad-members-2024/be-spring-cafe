package codesquad.springcafe.article;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ArticleLoggingAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // ArticleController 클래스 내의 모든 메서드 호출 전에 로그
    @Before("execution(* codesquad.springcafe.article.ArticleController.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        log.info("메서드 : {}, 파라미터 : {}", joinPoint.getSignature().getName(),
            joinPoint.getArgs());
    }

    // ArticleController 클래스 내의 모든 메서드 호출 후에 로그
    @AfterReturning(pointcut = "execution(* codesquad.springcafe.article.ArticleController.*(..))", returning = "result")
    public void afterControllerMethod(JoinPoint joinPoint, Object result) {
        log.info("메서드 실행 : {}, 결과 : {}", joinPoint.getSignature().getName(),
            result);
    }
}
