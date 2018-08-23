package com.wolftechnica.gc.storage.controllers;

import java.io.IOException;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Bucket;
import com.wolftechnica.gc.storage.service.GoogleClouldStorageService;

@RestController("/api/gcp/bucket")
public class GoogleCloudContoller {

	@Autowired
	private Environment environment; 
	
	@Autowired
	private GoogleClouldStorageService clouldStorageService;
	
	
	@PostMapping("/bucket")
	public APIResponse createCloudStorage(@RequestParam String mobile){
		clouldStorageService.CreateBucket(mobile);
		return new APIResponse(true, 200, "bucket created");
		
	}

	
	@GetMapping("/bucket")
	public APIResponse getCloudStorage(@RequestParam String mobile){
		Bucket bucket = clouldStorageService.getBucket(mobile);
		APIResponse apiResponse = new APIResponse(true, 200, "bucket fetched");
		apiResponse.setResult(bucket.getMetageneration());
		return apiResponse;
		
	}
	
	
	@PostMapping("/bucket/file")
	  public APIResponse uploadFileInBucket(
	            @RequestParam("file") MultipartFile uploadfile, String bucketName) throws IOException{
		clouldStorageService.addFileToBucket(uploadfile, bucketName);
		return new APIResponse(true, 200, "bucket created");
		
	}
	
	 

	
}
