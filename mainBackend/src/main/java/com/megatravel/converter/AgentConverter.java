package com.megatravel.converter;

import com.megatravel.dto.response.ResponseAgent;
import com.megatravel.dto.soap.CreateAgentRequest;
import com.megatravel.model.Agent;

public class AgentConverter extends AbstractConverter {
	
	public static ResponseAgent toResponseFromEntity(Agent agent) {
		ResponseAgent response = new ResponseAgent(agent.getUsername(), agent.getPassword(), agent.getEmail(), agent.getFirstName(), agent.getLastName(), agent.getBrn());
		response.setAddress(AddressConverter.toResponseFromEntity(agent.getAddress()));
		response.setRoles(RoleConverter.fromEntityList(agent.getRoles(), role -> RoleConverter.toResponseFromEntity(role)));
		return response;
	}
	
	public static Agent toEntityFromRequest(CreateAgentRequest request) {
		Agent agent = new Agent();
		
		agent.setUsername(request.getUsername());
		agent.setEmail(request.getEmail());
		agent.setFirstName(request.getFirstName());
		agent.setLastName(request.getLastName());
		agent.setPassword(request.getPassword());
		agent.setBrn(request.getBrn());
		
		return agent;
	}
	
}
