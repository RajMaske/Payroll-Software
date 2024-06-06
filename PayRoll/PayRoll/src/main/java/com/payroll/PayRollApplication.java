package com.payroll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan
@EntityScan("com.payroll.entity")
@EnableJpaRepositories("com.payroll.*")
public class PayRollApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayRollApplication.class, args);
	}

}
