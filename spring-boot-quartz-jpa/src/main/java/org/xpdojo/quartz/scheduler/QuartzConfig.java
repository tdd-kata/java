package org.xpdojo.quartz.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Quartz Cron 표현식의 경우 Unix Cron 표현식과 다르게 초(second) 단위가 있다.
 *
 * @see <a href="http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html">Cron Trigger Tutorial</a>
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

    private final Scheduler scheduler;

    @PostConstruct
    public void run() {
        try {
            scheduler.clear();
            scheduler.getListenerManager().addJobListener(new QuartzJobListener());
            scheduler.getListenerManager().addTriggerListener(new QuartzTriggerListener());

            final JobDetail detail = job(LoggingJob.class, new HashMap<>());
            final Trigger trigger = cronTrigger("0/3 * * * * ?");

            // 스케줄러에 Job 등록
            scheduler.scheduleJob(detail, trigger);
        } catch (SchedulerException exception) {
            log.error("스케줄러 등록 실패", exception);
        }
    }

    /**
     * 스케줄러에 등록할 Job을 생성한다.
     */
    public JobDetail job(
            Class<? extends Job> job,
            Map<String, Object> params) {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);

        return JobBuilder
                .newJob(job)
                .usingJobData(jobDataMap)
                .build();
    }

    /**
     * Job을 실행할 Trigger를 생성한다.
     * 여기서는 Cron 표현식을 사용하여 실행 주기를 설정한다.
     */
    public Trigger cronTrigger(String expression) {

        CronScheduleBuilder cronScheduleBuilder =
                CronScheduleBuilder.cronSchedule(expression);

        return TriggerBuilder
                .newTrigger()
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
