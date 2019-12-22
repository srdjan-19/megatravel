package com.megatravel.service;

import java.util.List;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.converter.AgentConverter;
import com.megatravel.converter.EndUserConverter;
import com.megatravel.dto.soap.CreateAgentRequest;
import com.megatravel.dto.soap.CreateEndUserRequest;
import com.megatravel.dto.soap.UserResponse;
import com.megatravel.exception.ExceptionResponse;
import com.megatravel.model.Agent;
import com.megatravel.model.EndUser;
import com.megatravel.model.Reservation;
import com.megatravel.model.Roles;
import com.megatravel.model.User;
import com.megatravel.model.UserStatus;
import com.megatravel.repository.UserRepository;


@Service
public class UserService {
	
	private final String AGENT_APP = "https://localhost:8443/agent-backend/";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private RoleService roleService;

	@Transactional(readOnly = true)
	public User findUser(String username) {
		return userRepository.findUserByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public EndUser findEndUser(String username) {
		return userRepository.findEndUserByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public EndUser findEndUserByEmail(String email) {
		return userRepository.findEndUserByEmail(email);
	}
	
	@Transactional(readOnly = true)
	public Agent findAgent(String username) {
		return userRepository.findAgentByUsername(username);
	}
	
	@Transactional(readOnly = true)
	public List<EndUser> findEndUsers() {
		return userRepository.findEndUsers();
	}
	
	@Transactional(readOnly = true)
	public List<Agent> findAgents() {
		return userRepository.findAgents();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(User eu) {
		userRepository.save(eu);
	}
	
	@Transactional(rollbackFor = { Exception.class, SOAPException.class } )
	public UserResponse completeEndUser(CreateEndUserRequest request) throws Exception {
		
		EndUser client = EndUserConverter.toEntityFromRequest(request);
		
		client.getRoles().add(roleService.findByName(Roles.END_USER));
		
		userRepository.save(client);
		
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
            
            UserResponse response = (UserResponse) webServiceTemplate.marshalSendAndReceive(AGENT_APP + "booking/users",request);
	          
//            UserResponse response = new UserResponse();
//            response.setFeedback("Created");
            return response;
            
		} catch (Exception s) {
			s.printStackTrace();
            return null;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void completeAgent(CreateAgentRequest request) {		
		Agent agent = AgentConverter.toEntityFromRequest(request);
		
		agent.setAddress(addressService.findByCity(request.getCity()));
		agent.getRoles().add(roleService.findByName(Roles.AGENT));
		
		userRepository.save(agent);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<EndUser> delete(String username) {
		EndUser enduser = userRepository.findEndUserByUsername(username);
		
		if (enduser == null)
			throw new ExceptionResponse("Client with username '" + username + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		userRepository.delete(enduser);
			
		return userRepository.findEndUsers();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<EndUser> activate(String username) {
		EndUser enduser = userRepository.findEndUserByUsername(username);
		
		if (enduser == null)
			throw new ExceptionResponse("Client with username '" + username + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		enduser.setStatus(UserStatus.ACTIVE);
		userRepository.save(enduser);
			
		return userRepository.findEndUsers();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<EndUser> block(String username) {
		EndUser enduser = userRepository.findEndUserByUsername(username);
		
		if (enduser == null)
			throw new ExceptionResponse("Client with username '" + username + "' does not exist!", HttpStatus.BAD_REQUEST);
		
		enduser.setStatus(UserStatus.BLOCKED);
		userRepository.save(enduser);
			
		return userRepository.findEndUsers();
	}

	@Transactional(readOnly = true)
	public List<Reservation> findMyReservations() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EndUser enduser = userRepository.findEndUserByUsername(authentication.getName());
		
		if (enduser == null)
			throw new ExceptionResponse("Client with username does not exist!", HttpStatus.BAD_REQUEST);
		
		return enduser.getReservations();
	}
	
	@Transactional(readOnly = true)
	public List<Agent> findMyInbox(long clientId) {
		return userRepository.findMyInbox(clientId);
	}

}
