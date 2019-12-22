//package com.megatravel.service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.megatravel.model.Accommodation;
//import com.megatravel.model.ImageResource;
//import com.megatravel.repository.AccommodationRepository;
//import com.megatravel.run.repository.ImageRepository;
//
//@Service
//public class ImageService {
//
//	@Autowired
//	private ImageRepository imageRepository;
//	
//	@Autowired
//	AccommodationRepository accommodationRepository;
//	
//	public void uploadImages(MultipartFile[] images, long accId) throws IOException {
//	
//		Accommodation acc = accommodationRepository.findById(accId);
//		
//		ArrayList<ImageResource> imagesModel = new ArrayList<>();
//		
//		for (MultipartFile img  : images) {
//			ImageResource imgModel = new ImageResource();
//			imgModel.setPic(img.getBytes());
//			acc.getImage().add(imgModel);
//			imagesModel.add(imgModel);
//		}
//		imageRepository.saveAll(imagesModel);	
//		
//		
//	}
//
//	public byte[] getImageById(long id){
//		ImageResource img = imageRepository.getOne(id);
//		return img.getPic();
//		
//	}
//
//	public List<Long> getImagesIdsByAcc(long id){
//		return imageRepository.getImagesId(id);
//	}
//	
//}
