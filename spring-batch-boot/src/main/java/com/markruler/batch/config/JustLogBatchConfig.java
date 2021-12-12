package com.markruler.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
// @Configuration
public class JustLogBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JustLogBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(firstStep())
                // .on("FAILED")
                // .to(thirdStep())
                .next(secondStep())
                // .end()
                .build();
    }

    @Bean
    public Step firstStep() {
        return stepBuilderFactory.get("firstStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> first step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step secondStep() {
        return stepBuilderFactory.get("secondStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> second step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step thirdStep() {
        return stepBuilderFactory.get("thirdStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> third step");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
