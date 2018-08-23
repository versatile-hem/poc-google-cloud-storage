package com.wolftechnica.gc.storage.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.wolftechnica.gc.storage.service.core.GoogleClouldStorageCoreService;
import com.wolftechnica.gc.storage.service.core.GoogleException;

@Service
public class GoogleClouldStorageService {

	@Autowired
	private GoogleClouldStorageCoreService clouldStorageCoreService;

	public void createBucket(String bucketName) {
		clouldStorageCoreService.createBucket(bucketName);

	}

	public Bucket getBucket(String bucketName) {
		return clouldStorageCoreService.getBucket(bucketName);
	}

	public void addFileToBucket(MultipartFile uploadfile, String bucketName) throws IOException {
		clouldStorageCoreService.addFileToBucket(uploadfile, bucketName);
	}

	public void uploadFileInFolder(MultipartFile uploadfile, String bucketName,String subDirectory ,long generation) throws IOException {
		clouldStorageCoreService.addFileToBucket(uploadfile, bucketName,subDirectory,  generation);
	}

	public List<BlobDTO> getObjectMediaLinks(String bucketName) throws GoogleException {
		Page<Blob> blobs = clouldStorageCoreService.getObjectsFromBucket(bucketName);
		List<BlobDTO> list = new ArrayList<>();
		for (Blob blob : blobs.iterateAll()) {
			BlobDTO blobDTO = new BlobDTO();
			blobDTO.setBlobId(blob.getBlobId().toString());
			blobDTO.setMediaLink(blob.getMediaLink());
			list.add(blobDTO);
		}
		return list;
	}

	public BlobDTO getFileFromBucket(String bucketName, String blobName, long generation) throws GoogleException {
		Blob blob = clouldStorageCoreService.getObjectFromBucket(bucketName, blobName, generation);
		BlobDTO blobDTO = new BlobDTO();
		blobDTO.setBlobId(blob.getGeneratedId());
		blobDTO.setMediaLink(blob.getMediaLink());
		return blobDTO;
		
	}

}
