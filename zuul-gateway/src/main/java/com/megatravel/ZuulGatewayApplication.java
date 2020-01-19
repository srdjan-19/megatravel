package com.megatravel;


import java.security.NoSuchAlgorithmException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl.EurekaJerseyClientBuilder;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@ComponentScan(basePackages = {"com.megatravel.config"})
@EnableFeignClients("com.megatravel.*")
public class ZuulGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulGatewayApplication.class, args);
	}
	

	@Bean
	public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws NoSuchAlgorithmException {
	    DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
	    System.setProperty("javax.net.ssl.keyStore", "src/main/resources/store/zuul.jks");
	    System.setProperty("javax.net.ssl.keyStorePassword", "megatravel");
	    System.setProperty("javax.net.ssl.trustStore", "src/main/resources/store/zuul.jks");
	    System.setProperty("javax.net.ssl.trustStorePassword", "megatravel");
	    EurekaJerseyClientBuilder builder = new EurekaJerseyClientBuilder();
	    builder.withClientName("zuul-gateway");
	    builder.withSystemSSLConfiguration();
	    builder.withMaxTotalConnections(10);
	    builder.withMaxConnectionsPerHost(10);
	    args.setEurekaJerseyClient(builder.build());
	    return args;
	}
	
}

