package org.xpdojo.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

// To enable support for scheduling tasks
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * fixedDelay 속성은 이전 작업이 종료된 후 설정시간(milliseconds) 이후에 다시 시작.
     * 이전 작업이 완료될 때까지 대기.
     * <p></p>
     * fixedRate 이전 작업의 완료 여부와 상관없이 설정시간(milliseconds) 이후에 다시 시작하지만,
     * 이전 작업이 완료될 때까지 다음 작업을 실행하지 않음.
     * <p></p>
     */
    @Scheduled(fixedDelay = 1_000)
    // @Scheduled(fixedRate = 1_000)
    public void fixedTask() throws InterruptedException {
        log.info("Fixed task - {}", System.currentTimeMillis() / 1000);
        Thread.sleep(2_000);
    }

    /**
     * @see org.springframework.scheduling.support.CronExpression
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void cronTask() {
        log.info("Cron task - {}", System.currentTimeMillis() / 1000);
    }

}
