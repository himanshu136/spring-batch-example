package com.batch.processor.jobs.batch;

import com.batch.processor.jobs.entity.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Locale;

//This is item processor for transforming the data.
@Component
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee item) throws Exception {
        item.setJob(item.getJob()!=null?item.getJob().toUpperCase(Locale.ENGLISH)
                :null);
        return item;
    }
}
