package com.santander.springbatchmysqlcsv.processor;

import com.santander.springbatchmysqlcsv.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee,Employee> {
    @Override
    public Employee process(Employee employee) throws Exception {
        System.out.println("Transfering employeess::"+employee);
        return employee;
    }
}
