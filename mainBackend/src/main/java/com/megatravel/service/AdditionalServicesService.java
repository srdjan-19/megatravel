package com.megatravel.service;

import java.util.List;

import javax.xml.soap.MessageFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.AdditionalServiceConverter;
import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAdditionalServiceRequest;
import com.megatravel.dto.soap.DeleteAdditionalServiceRequest;
import com.megatravel.dto.soap.UpdateAdditionalServiceRequest;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.AdditionalService;
import com.megatravel.repository.AdditionalServiceRepository;

@Service
public class AdditionalServicesService {
	
	private final String AGENT_APP = "https://localhost:8443/agent-backend/";
	
	@Autowired
	private AdditionalServiceRepository asRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(rollbackFor = Exception.class)
	public AdditionalService save(AdditionalService aService) {
		return asRepository.save(aService);
		
	}
	
	@Transactional(readOnly = true)
	public List<AdditionalService> findAll() {
		return asRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AdditionalService findById(Long id) {
		return asRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AdditionalService findByName(String name) {
		return asRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AdditionalService> create(CreateAdditionalServiceRequest request) {
		if (asRepository.findByName(request.getName()) != null)
			throw new ExceptionResponse("Accommodation category '" + request.getName() + "' alerady exist!", HttpStatus.BAD_REQUEST);
		
		AdditionalService accommodation = AdditionalServiceConverter.toEntityFromRequest(request);
		asRepository.save(accommodation);
		
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
            
            CodebookResponse response = (CodebookResponse) soapConnector.callWebService(AGENT_APP + "booking/codebook", request);
			            
    		return asRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return asRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AdditionalService> modify(UpdateAdditionalServiceRequest request) {
		AdditionalService category = asRepository.findByName(request.getOldName());
		
		if (category == null)
			throw new ExceptionResponse("Accommodation category '" + request.getOldName() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		if (asRepository.findByName(request.getNewName()) != null)
			throw new ExceptionResponse("Accommodation category '" + request.getNewName() + "' alerady exist!", HttpStatus.BAD_REQUEST);
		
		asRepository.modifyAS(category.getId(), request.getNewName());
		
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
            
            CodebookResponse response = (CodebookResponse) soapConnector.callWebService(AGENT_APP + "booking/codebook", request);
			            
    		return asRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return asRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AdditionalService> delete(String name) {
		AdditionalService category = asRepository.findByName(name);
		if (category == null)
			throw new ExceptionResponse("Accommodation category '" + name + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		asRepository.delete(category);
		
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
            
            DeleteAdditionalServiceRequest request = new DeleteAdditionalServiceRequest();
            request.setName(name);
            CodebookResponse response = (CodebookResponse) soapConnector.callWebService(AGENT_APP + "booking/codebook", request);
			            
    		return asRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return asRepository.findAll();
	}

}
