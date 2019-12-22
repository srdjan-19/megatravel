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
import com.megatravel.converter.AccommodationCategoryConverter;
import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAccommodationCategoryRequest;
import com.megatravel.dto.soap.DeleteAccommodationCategoryRequest;
import com.megatravel.dto.soap.UpdateAccommodationCategoryRequest;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.AccommodationCategory;
import com.megatravel.repository.AccommodationCategoryRepository;

@Service
public class AccommodationCategoryService {

	private final String AGENT_APP = "https://localhost:8443/agent-backend/";
	
	@Autowired
	private AccommodationCategoryRepository acRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationCategory save(AccommodationCategory aCategory) {
		return acRepository.save(aCategory);	
	}
	
	@Transactional(readOnly = true)
	public List<AccommodationCategory> findAll() {
		return acRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AccommodationCategory findById(Long id) {
		return acRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AccommodationCategory findByName(String name) {
		return acRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AccommodationCategory> create(CreateAccommodationCategoryRequest request) {
		if (acRepository.findByName(request.getName()) != null)
			throw new ExceptionResponse("Accommodation category '" + request.getName() + "' alerady exist!", HttpStatus.BAD_REQUEST);
		
		AccommodationCategory accommodation = AccommodationCategoryConverter.toEntityFromRequest(request);
		acRepository.save(accommodation);
		
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
			            
    		return acRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return acRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AccommodationCategory> modify(UpdateAccommodationCategoryRequest request) {
		AccommodationCategory category = acRepository.findByName(request.getOldName());
		
		if (category == null)
			throw new ExceptionResponse("Accommodation category '" + request.getOldName() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		if (acRepository.findByName(request.getNewName()) != null)
			throw new ExceptionResponse("Accommodation category '" + request.getNewName() + "' alerady exist!", HttpStatus.BAD_REQUEST);
		
		acRepository.modifyCategory(category.getId(), request.getNewName());
		
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
			            
    		return acRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return acRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AccommodationCategory> delete(String name) {
		AccommodationCategory category = acRepository.findByName(name);
		if (category == null)
			throw new ExceptionResponse("Accommodation category '" + name + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		acRepository.delete(category);
		
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
            
            DeleteAccommodationCategoryRequest request = new DeleteAccommodationCategoryRequest();
            request.setName(name);
            CodebookResponse response = (CodebookResponse) soapConnector.callWebService(AGENT_APP + "booking/codebook", request);
			            
    		return acRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return acRepository.findAll();
	}
	
}
