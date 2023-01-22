package org.xpdojo.springbootautoconfigure;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    static {
        // -D debug
        // for org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport
        System.setProperty("debug", "true");
    }

    @Bean
    ApplicationRunner applicationRunner(ConditionEvaluationReport report) {
        return args -> {
            report.getConditionAndOutcomesBySource()
                    .entrySet()
                    .stream()
                    .filter(conditionAndOutcomesEntry ->
                            conditionAndOutcomesEntry.getValue().isFullMatch()) // Positive matches
                    .forEach(conditionAndOutcomesEntry -> {
                        System.out.println(conditionAndOutcomesEntry.getKey());
                        conditionAndOutcomesEntry.getValue().forEach(co ->
                                System.out.println(
                                        "\t condition: -> " + co.getCondition().getClass().getSimpleName()
                                                + "\n\t outcome  : -> " + co.getOutcome()
                                )
                        );
                        System.out.println();
                    });

            // spring-boot-starter: 19개
            // spring-boot-starter-web: 68개
            // spring-boot-starter-jdbc: 40개
            System.out.println(report.getConditionAndOutcomesBySource()
                    .entrySet()
                    .stream()
                    .filter(conditionAndOutcomesEntry ->
                            conditionAndOutcomesEntry.getValue().isFullMatch())
                    .count());
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
