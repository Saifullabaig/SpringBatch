package com.santander.springbatchmysqlcsv.configuration;

import com.santander.springbatchmysqlcsv.model.Employee;
import com.santander.springbatchmysqlcsv.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringBatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public DataSource dataSource(){
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/EMPLOYEEDB");
        dataSource.setUsername("root");
        dataSource.setPassword("example");
        return dataSource;
    }

    @Bean(destroyMethod="")
    public JdbcCursorItemReader<Employee> reader(){
        JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT first_name,last_name,company_name,address,city,county,state,zip FROM employee");
        reader.setRowMapper(new EmployeeRowMapper());
        return reader;
    }
    public class EmployeeRowMapper implements RowMapper<Employee> {

        @Override
        public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setFirstName(resultSet.getString("first_name"));
            employee.setLastName(resultSet.getString("last_name"));
            employee.setCompanyName(resultSet.getString("company_name"));
            employee.setAddress(resultSet.getString("address"));
            employee.setCity(resultSet.getString("city"));
            employee.setCounty(resultSet.getString("county"));
            employee.setState(resultSet.getString("state"));
            employee.setZip(resultSet.getString("zip"));
            return employee;
        }
    }

    @Bean
    public EmployeeProcessor processor(){
        return new EmployeeProcessor();
    }

    @Bean(destroyMethod="")
    public FlatFileItemWriter<Employee> writer(){
        System.out.println("inside writter");
        FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<>();
        writer.setResource(new ClassPathResource("output/employee.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Employee>(){{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Employee>(){{
                setNames(new String[] {"firstName","lastName","companyName","address",
                        "city","county","state","zip"});
            }});
        }});
        //writer.setShouldDeleteIfEmpty(true);
        //writer.setShouldDeleteIfExists(true);
        System.out.println(writer+"writter");
    return writer;
    }

    @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1").<Employee, Employee> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job exportEmployeeJob(){
        return jobBuilderFactory.get("exportEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}





