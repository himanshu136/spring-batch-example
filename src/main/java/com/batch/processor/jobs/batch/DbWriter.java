package com.batch.processor.jobs.batch;

import com.batch.processor.jobs.entity.Employee;
import com.batch.processor.jobs.repo.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbWriter implements ItemWriter<Employee> {

    private static final Logger logger = LoggerFactory.getLogger(DbWriter.class);

    private EmployeeRepo employeeRepo;

    @Autowired
    public DbWriter(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void write(Chunk<? extends Employee> employeesChunkList) throws Exception {
        logger.info("Savinig the employee's chunk size is {}",employeesChunkList.size());
        employeeRepo.saveAll(employeesChunkList.getItems());
    }
}
