package com.megatravel.config;

import javax.xml.soap.MessageFactory;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.dto.soap.CodebookResponse;

public class SOAPConnector extends WebServiceGatewaySupport {
		
	public Object callWebService(String url, Object request){
		try {
            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
            messageFactory.afterPropertiesSet();

            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("com.megatravel.model");
            marshaller.afterPropertiesSet();

            webServiceTemplate.setMarshaller(marshaller);
            webServiceTemplate.setUnmarshaller(marshaller);
            webServiceTemplate.afterPropertiesSet();
            
            return getWebServiceTemplate().marshalSendAndReceive(url, request);
			               
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return null;
		
    }
	
}