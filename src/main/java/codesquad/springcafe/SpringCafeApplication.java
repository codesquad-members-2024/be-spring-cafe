package codesquad.springcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringCafeApplication {
	//여기 패키지부터 하위패키지까지 스캔

	public static void main(String[] args) {
		SpringApplication.run(SpringCafeApplication.class, args);
	}
}