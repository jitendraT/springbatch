package com.batch.config;

import com.batch.processor.CustomProcessor;
import com.batch.processor.CustomReader;
import com.batch.processor.CustomWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    Job createJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .flow(createStep(jobRepository, transactionManager)).end().build();
    }

    @Bean
    Step createStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<String, String> chunk(3, transactionManager)
                .allowStartIfComplete(true)
                .reader(new CustomReader())
                .processor(new CustomProcessor())
                .writer(new CustomWriter())
                .build();
    }
}

