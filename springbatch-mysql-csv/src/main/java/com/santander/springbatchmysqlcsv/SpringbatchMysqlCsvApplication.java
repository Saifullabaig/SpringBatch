package com.santander.springbatchmysqlcsv;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbatchMysqlCsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchMysqlCsvApplication.class, args);
	}

}
