package com.wolftechnica.gc.storage.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;
import com.google.cloud.storage.Bucket;
import com.wolftechnica.gc.storage.service.BlobDTO;
import com.wolftechnica.gc.storage.service.GoogleClouldStorageService;
import com.wolftechnica.gc.storage.service.core.GoogleException;

@RestController("/api/gcp/bucket")
public class GoogleCloudContoller {

	@Autowired
	private GoogleClouldStorageService clouldStorageService;

	@PostMapping("/bucket")
	public APIResponse createCloudStorage(@RequestParam String bucketName) {
		clouldStorageService.createBucket(bucketName);
		return new APIResponse(true, 200, "bucket created");

	}

	@GetMapping("/bucket")
	public APIResponse getCloudStorage(@RequestParam String bucketName) {
		Bucket bucket = clouldStorageService.getBucket(bucketName);
		APIResponse apiResponse = new APIResponse(true, 200, "bucket fetched");
		apiResponse.setResult(bucket.getMetageneration());
		return apiResponse;

	}

	@PostMapping("/bucket/file")
	public APIResponse uploadFileInBucket(@RequestParam("file") MultipartFile uploadfile,
			@RequestParam String bucketName, @RequestParam(required = false) long generation, String subDirectory)
			throws IOException {
		clouldStorageService.uploadFileInFolder(uploadfile, bucketName, subDirectory, generation);
		return new APIResponse(true, 200, "file uplaoded created");

	}

	@GetMapping("/bucket/file")
	public APIResponse getFileFromBucket(@RequestParam String bucketName, String blobName, long generation) {
		APIResponse apiResponse = new APIResponse(true, 200, "file fetched");
		try {
			BlobDTO blobDTO = clouldStorageService.getFileFromBucket(bucketName, blobName, generation);
			apiResponse.setResult(blobDTO);
		} catch (GoogleException ge) {
			return new APIResponse(false, ge.getExceptionCodes().getCode(), ge.getExceptionCodes().getDescription());
		}
		return apiResponse;

	}

	@GetMapping("/bucket/olist")
	public APIResponse getObjectINBucket(@RequestParam String bucketName) {
		try {
			APIResponse apiResponse = new APIResponse(true, 200, "medialinks of objects under bucket : " + bucketName);
			List<BlobDTO> list = clouldStorageService.getObjectMediaLinks(bucketName);
			apiResponse.setResult(list);
			return apiResponse;
		} catch (GoogleException ge) {
			return new APIResponse(false, ge.getExceptionCodes().getCode(), ge.getExceptionCodes().getDescription());
		}

	}

}
