package com.megatravel.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.megatravel.dto.soap.CreateAgentRequest;
import com.megatravel.dto.soap.UserResponse;
import com.megatravel.service.UserService;

@Endpoint
public class UserEndpoint {

	private static final String NAMESPACE_URI = "http://www.megatravel.com/users";

	private final UserService userService;
			
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAgentRequest")
    @ResponsePayload
    public UserResponse create(@RequestPayload CreateAgentRequest request) {
		userService.completeAgent(request);

		UserResponse response = new UserResponse();
		response.setFeedback("Agent '" + request.getFirstName() + " " + request.getLastName()  +"' has been registred!");
		
        return response;
    }

}
