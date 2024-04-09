package codesquad.springcafe;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LogTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void logTest() {
        logger.debug("fail");
        logger.info("success");
    }
}
