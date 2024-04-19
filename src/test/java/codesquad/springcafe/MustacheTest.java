package codesquad.springcafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MustacheTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("HTMl partial 테스트")
    @Test
    void partialTest() {
        // given
        // when
        String body = this.restTemplate.getForObject("/", String.class);

        //then
        assertThat(body).contains("<!-- script references -->");
    }
}
