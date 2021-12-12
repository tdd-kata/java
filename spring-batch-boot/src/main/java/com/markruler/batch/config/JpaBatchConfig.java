package com.markruler.batch.config;

import com.markruler.batch.model.Customer;
import com.markruler.batch.writer.ConsoleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManagerFactory;
import java.util.Collections;

/**
 * Definitive Guide to Spring Batch, 2/e (p321) -
 * <a href="https://github.com/Apress/def-guide-spring-batch">GitHub Source</a>
 *
 * @author Michael Minella
 */
@org.springframework.context.annotation.Configuration
public class JpaBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JpaBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    //	@Bean
    //	@StepScope
    //	public JpaPagingItemReader<Customer> customerItemReader(
    //			EntityManagerFactory entityManagerFactory,
    //			@Value("#{jobParameters['city']}") String city) {
    //
    //		return new JpaPagingItemReaderBuilder<Customer>()
    //				.name("customerItemReader")
    //				.entityManagerFactory(entityManagerFactory)
    //				.queryString("select c from Customer c where c.city = :city")
    //				.parameterValues(Collections.singletonMap("city", city))
    //				.build();
    //	}

    @Bean
    @StepScope
    public JpaPagingItemReader<Customer> customerItemReader(
            EntityManagerFactory entityManagerFactory,
            @Value("#{jobParameters['requestDate']}") String jobParamter) {

        System.out.println("jobParameter >>>>>>>>>>>>> " + jobParamter);
        final String city = "seoul";

        // JPA의 Query API를 사용하기 위해서는 JpaQueryProvider를 구현해야 한다.
        CustomerByCityQueryProvider queryProvider = new CustomerByCityQueryProvider();

        return new JpaPagingItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryProvider(queryProvider)
                .parameterValues(Collections.singletonMap("city", city))
                .build();
    }

    @Bean
    public ItemWriter<Customer> itemWriter() {
        return items -> items.forEach(System.out::println);
    }

    @Bean
    public Step copyFileStep() {
        return this.stepBuilderFactory.get("copyFileStep")
                .<Customer, Customer>chunk(10)
                .reader(customerItemReader(null, null))
                // .writer(itemWriter())
                .writer(new ConsoleItemWriter())
                .build();
    }

    /**
     * 동일한 Job이 Job Parameter가 달라지면 그때마다 BATCH_JOB_INSTANCE에 생성되며,
     * 동일한 Job Parameter는 여러개 존재할 수 없습니다. -
     * <a href="https://jojoldu.tistory.com/326">jojoldu</a>
     * <p>
     * "A job instance already exists and is complete for parameters={key=value}"
     */
    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(copyFileStep())
                .build();
    }

}
