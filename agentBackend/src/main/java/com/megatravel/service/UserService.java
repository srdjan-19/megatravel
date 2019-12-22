package com.megatravel.service;

import java.util.List;

import javax.xml.soap.MessageFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.megatravel.converter.AgentConverter;
import com.megatravel.dto.soap.CreateAgentRequest;
import com.megatravel.dto.soap.UserResponse;
import com.megatravel.model.Agent;
import com.megatravel.model.EndUser;
import com.megatravel.model.Message;
import com.megatravel.model.Reservation;
import com.megatravel.model.Roles;
import com.megatravel.model.User;
import com.megatravel.repository.UserRepository;


@Service
public class UserService {
	
	private final String MAIN_APP = "https://localhost:8443/main-backend/";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ReservationService reservationService;
	
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
	public Agent findAgent(String username) {
		return userRepository.findAgentByUsername(username);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void save(User eu) {
		userRepository.save(eu);
	}

	@Transactional(rollbackFor = Exception.class)
	public List<Agent> complete(CreateAgentRequest request) {
		
		Agent agent = AgentConverter.toEntityFromRequest(request);
		
		agent.setAddress(addressService.findByCity(request.getCity()));
		agent.getRoles().add(roleService.findByName(Roles.AGENT));
		
		try {
            SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory(MessageFactory.newInstance());
            messageFactory.afterPropertiesSet();

            WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory);
            Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

            marshaller.setContextPath("com.megatravel.dto.soap");
            marshaller.afterPropertiesSet();

            webServiceTemplate.setMarshaller(marshaller);
            webServiceTemplate.setUnmarshaller(marshaller);
            webServiceTemplate.afterPropertiesSet();

            UserResponse response = (UserResponse) webServiceTemplate.marshalSendAndReceive(MAIN_APP + "booking/users",request);
                        
    		userRepository.save(agent);

            return userRepository.findAgents();

        } catch (Exception s) {
        	s.printStackTrace();
        }
		
        return userRepository.findAgents();

	}

	@Transactional(readOnly = true)
	public List<EndUser> findMyInbox(long agentId) {
		return userRepository.findMyInbox(agentId);
	}

	@Transactional(readOnly = true)
	public List<Reservation> findMyReservations() {
		return reservationService.findMyReservations();
	}
	
	
}
