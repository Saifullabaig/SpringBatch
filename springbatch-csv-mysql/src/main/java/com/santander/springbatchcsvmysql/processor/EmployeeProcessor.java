package com.santander.springbatchcsvmysql.processor;

import com.santander.springbatchcsvmysql.model.Employee;
import com.santander.springbatchcsvmysql.model.EmployeeDTO;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, EmployeeDTO> {

    @Override
    public EmployeeDTO process(Employee employee) throws Exception {
        System.out.println("Transforming Employee(s) to EmployeeDTO(s)...."+employee);
        final EmployeeDTO employeeDTO =  new EmployeeDTO(employee.getFirstName(), employee.getLastName(),
                employee.getCompanyName(), employee.getAddress(),employee.getCity(),employee.getCounty(),employee.getState()
                ,employee.getZip());
        return employeeDTO;
    }
}
