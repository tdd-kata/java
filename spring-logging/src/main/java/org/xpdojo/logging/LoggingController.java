package org.xpdojo.logging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @see org.slf4j.Logger
 * @see org.apache.logging.log4j.Logger
 * @see java.util.logging.Logger
 */
@Slf4j
@RestController
public class LoggingController {

    private static final java.util.logging.Logger JUL = java.util.logging.Logger.getLogger(LoggingController.class.getName());
    private static final org.slf4j.Logger SLF4J = LoggerFactory.getLogger(LoggingController.class);
    private static final org.apache.logging.log4j.Logger LOG4J = org.apache.logging.log4j.LogManager.getLogger(LoggingController.class);

    /**
     * <pre>
     * curl http://localhost:8080
     * </pre>
     */
    @GetMapping("/")
    public Response index() {

        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");

        JUL.info("JUL"); // LOG4J 로그 파일에 남지 않음
        SLF4J.info("SLF4J - {}", "arg1");
        LOG4J.info("LOG4J - {}", "arg1");

        Response response = Response.builder()
                .message("Hello World")
                .build();

        log.info("response: {}", response);

        return response;
    }

}
