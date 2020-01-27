package com.megatravel.run;

import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;


@SpringBootApplication	
@EnableFeignClients("com.megatravel.*")
@ComponentScan(basePackages = {"com.megatravel.config",
							   "com.megatravel.controller",
							   "com.megatravel.endpoints",
							   "com.megatravel.exception", 
							   "com.megatravel.controller",
							   "com.megatravel.security",
							   "com.megatravel.service"
							  })
@EntityScan(basePackages = {"com.megatravel.model"})
@EnableJpaRepositories(basePackages = {"com.megatravel.repository"})
@EnableDiscoveryClient
public class MainBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainBackendApplication.class, args);
	}
	
	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
	    DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
	    System.setProperty("javax.net.ssl.keyStore", "src/main/resources/store/main-backend.jks");
	    System.setProperty("javax.net.ssl.keyStorePassword", "megatravel");
	    System.setProperty("javax.net.ssl.trustStore", "src/main/resources/store/main-backend.jks");
	    System.setProperty("javax.net.ssl.trustStorePassword", "megatravel");
	    EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
	    builder.withClientName("main-backend");
	    builder.withSystemSSLConfiguration();
	    builder.withMaxTotalConnections(10);
	    builder.withMaxConnectionsPerHost(10);
	    args.setEurekaJerseyClient(builder.build());
	    return args;
	}

}
