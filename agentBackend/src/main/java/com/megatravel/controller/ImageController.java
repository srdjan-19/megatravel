//package com.megatravel.controller;
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.megatravel.dto.AccommodationDTO;
//import com.megatravel.model.Accommodation;
//import com.megatravel.service.AccommodationService;
//import com.megatravel.service.ImageService;
//
//
//@RestController
//@RequestMapping("/images")
//public class ImageController {
//
//	@Autowired
//	privatea ImageService imageService;
//	
//	@Autowired
//	AccommodationService accService;
//	
//	@SuppressWarnings("rawtypes")
//	@PostMapping(value = "/upload", produces =  MediaType.IMAGE_PNG_VALUE)
//	public ResponseEntity uploadImages(@RequestParam("file") MultipartFile[] images, @RequestParam("accId") long accId){
//		try {
//			imageService.uploadImages(images, accId);
//			
//		} catch (IOException e) {
//			
//			return new ResponseEntity(HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity(HttpStatus.OK);
//	}
//	
//	
//	
//	@ResponseBody
//	@PostMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
//	public byte[] getImage(@RequestBody long id) {
////		byte[] binaryImage = imageService.getImageById(id);
//		return binaryImage;
//	}
//	
//	
//	@ResponseBody
//	@PostMapping(value = "/getImageIdsByAcc")
//	public List<Long> getImagesIdsByAcc(@RequestBody AccommodationDTO acc) {
//		
//		return imageService.getImagesIdsByAcc(acc.getId());
//		
//	}
//}
//
//
