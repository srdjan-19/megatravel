package com.megatravel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class XsdConfig {
	
	@Bean(name = "accommodationSchema")
	public XsdSchema accommodationSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("schemes/Accommodation.xsd"));
	}

	@Bean(name = "reservationSchema")
	public XsdSchema reservationSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("schemes/Reservation.xsd"));
	}
	
	@Bean(name = "messageSchema")
	public XsdSchema messageSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("schemes/Message.xsd"));
	}
	
	@Bean(name = "codebookSchema")
	public XsdSchema codebookSchema() {
	    return new SimpleXsdSchema(new ClassPathResource("schemes/Codebook.xsd"));
	}

}
