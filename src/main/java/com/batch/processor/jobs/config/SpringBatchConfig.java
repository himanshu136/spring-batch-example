package com.batch.processor.jobs.config;


import com.batch.processor.jobs.batch.EmployeeItemProcessor;
import com.batch.processor.jobs.batch.JobCompletionListener;
import com.batch.processor.jobs.entity.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class SpringBatchConfig {

    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .resource(new ClassPathResource("salaries.csv"))
                .strict(false)
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .build();
    }

    @Bean
    public LineMapper<Employee> lineMapper() {

        DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("name","age","job","salary");

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }



    @Bean
    public Job job(JobRepository jobRepository, Step step1,
                   JobCompletionListener listener){
        return new JobBuilder("Employee Job",jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager dataSourceTransactionManager,
                      FlatFileItemReader<Employee> reader, EmployeeItemProcessor processor,
                      ItemWriter<Employee> writer) {
        return new StepBuilder("step1", jobRepository)
                .<Employee,Employee>chunk(50,dataSourceTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
