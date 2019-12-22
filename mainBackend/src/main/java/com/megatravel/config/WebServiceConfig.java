package com.megatravel.config;

import java.util.HashMap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
	    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
	    servlet.setApplicationContext(applicationContext);
	    servlet.setTransformWsdlLocations(true);
	    return new ServletRegistrationBean(servlet, "/booking/*");
	}
	
	@Bean(name = "accommodation")
	public DefaultWsdl11Definition AccommodationsWsdl(XsdSchema accommodationSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("AccomodaitonsPort");
	    wsdl11Definition.setLocationUri("/booking");
	    wsdl11Definition.setTargetNamespace("http://www.megatravel.com/accommodation");
	    wsdl11Definition.setSchema(accommodationSchema);
	    return wsdl11Definition;
	}

	@Bean(name = "reservation")
	public DefaultWsdl11Definition ReservationsWsdl(XsdSchema reservationSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("ReservationsPort");
	    wsdl11Definition.setLocationUri("/booking");
	    wsdl11Definition.setTargetNamespace("http://www.megatravel.com/reservation");
	    wsdl11Definition.setSchema(reservationSchema);
	    return wsdl11Definition;
	}
	
	@Bean(name = "message")
	public DefaultWsdl11Definition MessagesWsdl(XsdSchema messageSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("MessagesPort");
	    wsdl11Definition.setLocationUri("/booking");
	    wsdl11Definition.setTargetNamespace("http://www.megatravel.com/message");
	    wsdl11Definition.setSchema(messageSchema);
	    return wsdl11Definition;
	}

	@Bean(name = "codebook")
	public DefaultWsdl11Definition CodebookWsdl(XsdSchema codebookSchema) {
	    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
	    wsdl11Definition.setPortTypeName("CodebookPort");
	    wsdl11Definition.setLocationUri("/booking");
	    wsdl11Definition.setTargetNamespace("http://www.megatravel.com/codebook");
	    wsdl11Definition.setSchema(codebookSchema);
	    return wsdl11Definition;
	}
	
	@Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPaths("com.megatravel.dto.soap");
        
        marshaller.setMarshallerProperties(new HashMap<String, Object>() {{ 
        	put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, true);
        }});
        
        WebServiceTemplate wst = new WebServiceTemplate();
        
        wst.setMarshaller(marshaller);
        wst.setUnmarshaller(marshaller);

        return marshaller;
    }

    @Bean
    public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
        SOAPConnector client = new SOAPConnector();
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
    
    @Bean
    public WebServiceTemplate WebServiceTemplate(Jaxb2Marshaller marshaller) {
    	WebServiceTemplate wst = new WebServiceTemplate(marshaller, marshaller);
    	
    	return wst;
    }

	 
}
