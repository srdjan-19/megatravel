package com.megatravel.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableFeignClients("com.megatravel.*")
@Configuration
@ComponentScan(basePackages= {"com.megatravel.service", 
							  "com.megatravel.controller", 
							  "com.megatravel.validator",
							   "com.megatravel.security"})
@EnableEurekaClient
public class AgentCreationMsApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		
		return new RestTemplate();
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder() {
		return WebClient.builder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AgentCreationMsApplication.class, args);
	}

}
