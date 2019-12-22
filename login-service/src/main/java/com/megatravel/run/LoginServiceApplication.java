package com.megatravel.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableFeignClients("com.megatravel.*")
@Configuration
@ComponentScan(basePackages= {"com.megatravel.controller", 
							  "com.megatravel.exception", 
							  "com.megatravel.security", 
							   "com.megatravel.service"})
@EnableEurekaClient
@CrossOrigin(value = "http://localhost:4200", maxAge = 3600)
public class LoginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

}
