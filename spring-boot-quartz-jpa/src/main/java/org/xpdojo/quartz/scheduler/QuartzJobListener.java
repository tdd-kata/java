package org.xpdojo.quartz.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class QuartzJobListener implements JobListener {

    @Override
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * JobDetail의 execute() 메서드가 실행되기 전에 실행된다.
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job To Be Executed");
    }

    /**
     * vetoJobExecution() 메서드가 true를 리턴하면 실행된다.
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Job Execution Vetoed");
    }

    /**
     * JobDetail의 execute() 메서드가 실행된 후에 실행된다.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job was executed");
    }
}
