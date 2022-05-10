package com.markruler.batch.config;

import com.markruler.batch.model.Product;
import com.markruler.batch.reader.ProductCsvFieldSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

/**
 * java -jar target/batch-*.jar --spring.profiles.active=test inputFile=input/product.csv
 */
@Slf4j
// @org.springframework.context.annotation.Configuration
public class CsvFileBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public CsvFileBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job csvFileJob() {
        return jobBuilderFactory.get("csvFileJob")
                .start(singleStep())
                .build();
    }

    @Bean
    public Step singleStep() {
        return stepBuilderFactory.get("singleStep")
                .<Product, Product>chunk(100)
                .reader(flatFileItemReader(null))
                .writer(items -> items.forEach(System.out::println))
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Product> flatFileItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource inputFile) {
        FlatFileItemReaderBuilder<Product> reader = new FlatFileItemReaderBuilder<>();
        return reader
                .name("productCsvItemReader")
                .delimited()
                .names("id", "name", "description", "price", "unit")
                .fieldSetMapper(new ProductCsvFieldSetMapper())
                .resource(inputFile)
                .build();
    }

}
