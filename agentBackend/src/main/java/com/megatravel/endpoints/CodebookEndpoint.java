package com.megatravel.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.megatravel.dto.soap.CodebookResponse;
import com.megatravel.dto.soap.CreateAccommodationCategoryRequest;
import com.megatravel.dto.soap.CreateAccommodationTypeRequest;
import com.megatravel.dto.soap.CreateAdditionalServiceRequest;
import com.megatravel.dto.soap.DeleteAccommodationCategoryRequest;
import com.megatravel.dto.soap.DeleteAccommodationTypeRequest;
import com.megatravel.dto.soap.DeleteAdditionalServiceRequest;
import com.megatravel.dto.soap.UpdateAccommodationCategoryRequest;
import com.megatravel.dto.soap.UpdateAccommodationTypeRequest;
import com.megatravel.dto.soap.UpdateAdditionalServiceRequest;
import com.megatravel.service.AccommodationCategoryService;
import com.megatravel.service.AccommodationTypeService;
import com.megatravel.service.AdditionalServicesService;


@Endpoint
public class CodebookEndpoint {
		
	private static final String NAMESPACE_URI = "http://www.megatravel.com/codebook";
	
	private final AdditionalServicesService additionalServices;
	
	private final AccommodationCategoryService categoryService;
	
	private final AccommodationTypeService typeService;
	
	public CodebookEndpoint(AdditionalServicesService additionalServices, AccommodationCategoryService categoryService,
			AccommodationTypeService typeService) {
		super();
		this.additionalServices = additionalServices;
		this.categoryService = categoryService;
		this.typeService = typeService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAdditionalServiceRequest")
    @ResponsePayload
    public CodebookResponse createAdditionalService(@RequestPayload CreateAdditionalServiceRequest request) {
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(additionalServices.create(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationCategoryRequest")
    @ResponsePayload
    public CodebookResponse createAccommodationCategory(@RequestPayload CreateAccommodationCategoryRequest request) {		
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(categoryService.create(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAccommodationTypeRequest")
    @ResponsePayload
    public CodebookResponse createAccommodationType(@RequestPayload CreateAccommodationTypeRequest request) {
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(typeService.create(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAdditionalServiceRequest")
    @ResponsePayload
    public CodebookResponse updateAdditionalService(@RequestPayload UpdateAdditionalServiceRequest request) {
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(additionalServices.modify(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAccommodationCategoryRequest")
    @ResponsePayload
    public CodebookResponse updateAccommodationCategory(@RequestPayload UpdateAccommodationCategoryRequest request) {
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(categoryService.modify(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAccommodationTypeRequest")
    @ResponsePayload
    public CodebookResponse updateAccommodationCategory(@RequestPayload UpdateAccommodationTypeRequest request) {
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(typeService.modify(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccommodationTypeRequest")
    @ResponsePayload
    public CodebookResponse deleteAccommodationType(@RequestPayload DeleteAccommodationTypeRequest request) {
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(typeService.delete(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAccommodationCategoryRequest")
    @ResponsePayload
    public CodebookResponse deleteAccommodationCategory(@RequestPayload DeleteAccommodationCategoryRequest request) {		
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(categoryService.delete(request));
		
        return response;
    }
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAdditionalServiceRequest")
    @ResponsePayload
    public CodebookResponse deleteAccommodationCategory(@RequestPayload DeleteAdditionalServiceRequest request) {		
		CodebookResponse response = new CodebookResponse();
		response.setFeedback(additionalServices.delete(request));
		
        return response;
    }
}
