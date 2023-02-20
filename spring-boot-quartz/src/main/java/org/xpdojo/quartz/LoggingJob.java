package org.xpdojo.quartz;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class LoggingJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(LoggingJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("Log!");
    }

}
