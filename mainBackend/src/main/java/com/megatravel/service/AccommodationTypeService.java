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
import com.megatravel.converter.AccommodationTypeConverter;
import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAccommodationTypeRequest;
import com.megatravel.dto.soap.DeleteAccommodationTypeRequest;
import com.megatravel.dto.soap.UpdateAccommodationTypeRequest;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.AccommodationType;
import com.megatravel.repository.AccommodationTypeRepository;

@Service
public class AccommodationTypeService {

	private final String AGENT_APP = "https://localhost:8443/agent-backend/";
	
	@Autowired
	private AccommodationTypeRepository atRepository;
	
	@Autowired
	private SOAPConnector soapConnector;
	
	@Transactional(rollbackFor = Exception.class)
	public AccommodationType save(AccommodationType aType) {
		return atRepository.save(aType);
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void delete(AccommodationType aType) {
		atRepository.delete(aType);
	}
	
	@Transactional(readOnly = true)
	public List<AccommodationType> findAll() {
		return atRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public AccommodationType findById(Long id) {
		return atRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public AccommodationType findByName(String name) {
		return atRepository.findByName(name);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AccommodationType> create(CreateAccommodationTypeRequest request) {
		if (atRepository.findByName(request.getName()) != null)
			throw new ExceptionResponse("Accommodation category '" + request.getName() + "' alerady exist!", HttpStatus.BAD_REQUEST);
		
		AccommodationType accommodation = AccommodationTypeConverter.toEntityFromRequest(request);
		atRepository.save(accommodation);
		
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
			            
    		return atRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return atRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AccommodationType> modify(UpdateAccommodationTypeRequest request) {
		AccommodationType category = atRepository.findByName(request.getOldName());
		
		if (category == null)
			throw new ExceptionResponse("Accommodation category '" + request.getOldName() + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		if (atRepository.findByName(request.getNewName()) != null)
			throw new ExceptionResponse("Accommodation category '" + request.getNewName() + "' alerady exist!", HttpStatus.BAD_REQUEST);
		
		atRepository.modifyType(category.getId(), request.getNewName());
		
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
			            
    		return atRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return atRepository.findAll();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<AccommodationType> delete(String name) {
		AccommodationType category = atRepository.findByName(name);
		if (category == null)
			throw new ExceptionResponse("Accommodation category '" + name + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		atRepository.delete(category);
		
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
            
            DeleteAccommodationTypeRequest request = new DeleteAccommodationTypeRequest();
            request.setName(name);
            CodebookResponse response = (CodebookResponse) soapConnector.callWebService(AGENT_APP + "booking/codebook", request);
			            
    		return atRepository.findAll();
    		
        } catch (Exception s) {
        	s.printStackTrace();
        }
		
		return atRepository.findAll();
	}
	
}
