package com.batch.processor.jobs.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionListener implements JobExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(JobCompletionListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Starting job with job id: {} and time {}",jobExecution.getExecutionContext()
        ,jobExecution.getCreateTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job {} finished, end time {}",jobExecution.getJobId(),
                jobExecution.getEndTime());
    }
}
