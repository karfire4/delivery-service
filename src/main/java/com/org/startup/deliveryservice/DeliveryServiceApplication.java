package com.org.startup.deliveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class,DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.org.startup.repository")
@ServletComponentScan(value="com.org.startup.security")
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EntityScan("com.org.startup.entity")  //JPA entities
@ComponentScan({"com.org.startup.service", "com.org.startup.controller","com.org.startup.exception"})
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

}
