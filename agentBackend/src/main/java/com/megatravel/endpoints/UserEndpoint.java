package com.megatravel.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.megatravel.converter.EndUserConverter;
import com.megatravel.dto.soap.CreateEndUserRequest;
import com.megatravel.model.EndUser;
import com.megatravel.model.Roles;
import com.megatravel.model.UserResponse;
import com.megatravel.service.RoleService;
import com.megatravel.service.UserService;

@Endpoint
public class UserEndpoint {

	private static final String NAMESPACE_URI = "http://www.megatravel.com/users";

	private UserService userService;
		
	private RoleService roleService;
	
    public UserEndpoint(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createEndUserRequest")
    @ResponsePayload
    public UserResponse create(@RequestPayload CreateEndUserRequest request) {
		
		UserResponse response = new UserResponse();
		
		EndUser client = EndUserConverter.toEntityFromRequest(request);
		client.getRoles().add(roleService.findByName(Roles.END_USER));
		
		userService.save(client);
		
		response.setFeedback("Client '" + request.getFirstName() + " " + request.getLastName()  +"' has been registred!");
		
        return response;
    }

}
