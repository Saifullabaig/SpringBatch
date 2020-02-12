package com.santander.springbatchcsvmysql;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class SpringbatchCsvMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchCsvMysqlApplication.class, args);
	}

}
