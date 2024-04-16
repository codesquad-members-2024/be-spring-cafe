package codesquad.springcafe;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링 설정 클래스임을 나타냄
@ComponentScan
// cedesquad.springcafe 패키지와 그 하위 패키지를 모두 스캔 대상으로 지정
// 만약 특정 패키지만 스캔하고 싶다면 @ComponentScan(basePackages = "원하는 패키지 경로") 와 같이 설정할 것
public class AutoAppConfig {
}
