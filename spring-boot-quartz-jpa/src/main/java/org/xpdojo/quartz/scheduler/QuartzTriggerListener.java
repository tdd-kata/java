package org.xpdojo.quartz.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

@Slf4j
public class QuartzTriggerListener implements TriggerListener {
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("||||||||||||");
        log.info("Trigger Fired");
    }

    /**
     * triggerFired() 메서드가 실행된 후에 실행된다.
     * 결과가 true이면  JobListener jobExecutionVetoed(JOB 중단) 실행되고,
     * JobDetail의 execute() 메서드가 실행되지 않는다.
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("Trigger Veto");

        JobDataMap map = context.getJobDetail().getJobDataMap();

        int executeCount = 1;
        if (map.containsKey("executeCount")) {
            executeCount = (int) map.get("executeCount");
        }

        return executeCount >= 2;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("Trigger Misfired");
    }

    @Override
    public void triggerComplete(
            Trigger trigger,
            JobExecutionContext context,
            Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.info("Trigger Complete");
        log.info("============");
    }

}
