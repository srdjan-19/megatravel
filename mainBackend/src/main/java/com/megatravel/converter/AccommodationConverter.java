package com.megatravel.converter;

import java.util.ArrayList;
import java.util.List;

import com.megatravel.dto.response.ResponseAccommodation;
import com.megatravel.dto.soap.CreateAccommodationRequest;
import com.megatravel.dto.soap.CudAccommodationResponse;
import com.megatravel.model.Accommodation;
import com.megatravel.model.AdditionalService;
import com.megatravel.model.ImageResource;



public class AccommodationConverter extends AbstractConverter {

	public static ResponseAccommodation toResponseFromEntity(Accommodation accommodation) {
		ResponseAccommodation response = new ResponseAccommodation(accommodation.getId(), accommodation.getName(), AccommodationTypeConverter.toResponseFromEntity(accommodation.getType()), AccommodationCategoryConverter.toResponseFromEntity(accommodation.getCategory()), 
																   accommodation.getDistance(), accommodation.getDescription(), accommodation.getCapacity());
		
		response.setCancellation(CanncellationConverter.toResponseFromEntity(accommodation.getCancellation()));
		response.setAddress(AddressConverter.toResponseFromEntity(accommodation.getAddress()));
		response.setOwnedBy(AgentConverter.toResponseFromEntity(accommodation.getOwnedBy()));
		response.setImages(ImageConverter.fromEntityList(accommodation.getImage(), img -> ImageConverter.toResponseFromEntity(img)));
		response.setAdditionalService(AdditionalServiceConverter.fromEntityList(accommodation.getAdditionalServices(), as -> AdditionalServiceConverter.toResponseFromEntity(as)));
		response.setRates(RateConverter.fromEntityList(accommodation.getRates(), rate -> RateConverter.toResponseFromEntity(rate)));
		response.setComments(CommentConverter.fromEntityList(accommodation.getComments(), comment -> CommentConverter.toResponseFromEntity(comment)));
		response.setPrices(PriceInSeasonConverter.fromEntityList(accommodation.getPriceInSeason(), pis -> PriceInSeasonConverter.toResponseFromEntity(pis)));
		
		return response;
	}
	
	public static Accommodation toEntityFromRequest(CreateAccommodationRequest request) {
		Accommodation accommodation = new Accommodation();

		accommodation.setCapacity(request.getCapacity());
		accommodation.setDescription(request.getDescription());
		accommodation.setDistance(request.getDistance());
		accommodation.setName(request.getName());
		
		List<String> imageUrl = request.getImages();
		List<ImageResource> imgResource = new ArrayList<ImageResource>();
		if(imageUrl != null) {
			for(String xs : imageUrl) {
				ImageResource newReso = new ImageResource();
				newReso.setPath(xs);
				imgResource.add(newReso);
			}
		}
		accommodation.setImage(imgResource);
		
		List<String> addServ = request.getAdditionalServices();
		List<AdditionalService> addServList = new ArrayList<AdditionalService>();
		if(addServ != null) {
			for(String xs : addServ) {
				AdditionalService newService = new AdditionalService();
				newService.setName(xs);
				addServList.add(newService);
			}
		}
		accommodation.setAdditionalServices(addServList);
	
		return accommodation;
	}
	
	public static CudAccommodationResponse toCudAccommodationResponse(Accommodation accommodation, String action) {
		return new CudAccommodationResponse("Accommodation " + accommodation.getId() + " has been " + action);
	}
}
