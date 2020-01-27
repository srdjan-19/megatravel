package com.megatravel.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.config.SOAPConnector;
import com.megatravel.converter.AgentConverter;
import com.megatravel.converter.EndUserConverter;
import com.megatravel.dto.soap.CreateAgentRequest;
import com.megatravel.dto.soap.CreateEndUserRequest;
import com.megatravel.dto.soap.UserResponse;
import com.megatravel.model.Agent;
import com.megatravel.model.EndUser;
import com.megatravel.model.Reservation;
import com.megatravel.model.Roles;
import com.megatravel.model.User;
import com.megatravel.model.UserStatus;
import com.megatravel.repository.UserRepository;


@Service
public class UserService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String USERS_ENDPOINT = "https://localhost:8443/agent-backend/booking/users";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private SOAPConnector soapService;
	
	@Transactional(readOnly = true)
	public User findUser(String username) {
		if (userRepository.findUserByUsername(username) == null)
			throw new EntityNotFoundException("User " + username + " was not found.");
		
		logger.info("User " + username + " was found.");
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
		logger.info("Retrieving clients for admin " + SecurityContextHolder.getContext().getAuthentication().getName());
		return userRepository.findEndUsers();
	}
	
	@Transactional(readOnly = true)
	public List<Agent> findAgents() {
		logger.info("Retrieving agents for admin " + SecurityContextHolder.getContext().getAuthentication().getName());
		return userRepository.findAgents();
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(User eu) {
		userRepository.save(eu);
		logger.info("User with id " + eu.getId() + " has been updated.");
	}
	
	@Transactional(rollbackFor = { Exception.class, SOAPException.class } )
	public UserResponse completeEndUser(CreateEndUserRequest request) throws Exception {
		
		logger.info("Completing user registration request...");
		EndUser client = EndUserConverter.toEntityFromRequest(request);
		
		logger.info("Defining the role of user...");
		client.getRoles().add(roleService.findByName(Roles.END_USER));
		
		if (userRepository.save(client) != null) 
			logger.info("Client registration is complete.");
		else
			logger.error("Invalid user registration request.");
		
        UserResponse response = (UserResponse) soapService.callWebService(USERS_ENDPOINT,request);

        return response;
	}

	@Transactional(rollbackFor = Exception.class)
	public void completeAgent(CreateAgentRequest request) {
		logger.info("Completing agent registration request...");
		Agent agent = AgentConverter.toEntityFromRequest(request);
		
		agent.setAddress(addressService.findById(request.getAddressId()));

		agent.getRoles().add(roleService.findByName(Roles.AGENT));
		
		userRepository.save(agent);
		logger.info("Agent registration is complete.");
	}

	@Transactional(rollbackFor = Exception.class)
	public EndUser delete(String username) {
		logger.info("Serching for user " + username);
		EndUser enduser = userRepository.findEndUserByUsername(username);
		
		if (enduser == null)
			throw new EntityNotFoundException("Client with username '" + username + "' does not exist!");
		
		userRepository.delete(enduser);
			
		return enduser;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public EndUser activate(String username) {
		logger.info("Serching for user " + username);

		EndUser enduser = userRepository.findEndUserByUsername(username);
		
		if (enduser == null)
			throw new EntityNotFoundException("Client with username '" + username + "' does not exist!");
		
		enduser.setStatus(UserStatus.ACTIVE);
		userRepository.save(enduser);

		logger.info("User " + username + " has been activated.");
	
		return enduser;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public EndUser block(String username) {
		logger.info("Serching for user " + username);

		EndUser enduser = userRepository.findEndUserByUsername(username);
		
		if (enduser == null)
			throw new EntityNotFoundException("Client with username '" + username + "' does not exist!");
		
		enduser.setStatus(UserStatus.BLOCKED);
		userRepository.save(enduser);
			
		logger.info("User " + username + " has been activated.");

		return enduser;
	}

	@Transactional(readOnly = true)
	public List<Reservation> findMyReservations() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		EndUser enduser = userRepository.findEndUserByUsername(authentication.getName());
		
		if (enduser == null)
			throw new EntityNotFoundException("Client with username does not exist!");

		logger.info("Retrieving " + enduser.getUsername() + "'s reservations.");

		return enduser.getReservations();
	}
	
	@Transactional(readOnly = true)
	public List<Agent> findMyInbox(long clientId) {
		logger.info("Searching for inbox of user with id " + clientId);

		return userRepository.findMyInbox(clientId);
	}

}
