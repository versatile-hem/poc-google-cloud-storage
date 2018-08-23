package com.wolftechnica.gc.storage.service.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobGetOption;
import com.google.cloud.storage.Storage.BucketListOption;
import com.google.cloud.storage.StorageOptions;

@Service
@PropertySource(value = { "google.properties" }, ignoreResourceNotFound = true)
public class GoogleClouldStorageCoreService {

	private Log log = LogFactory.getLog(GoogleClouldStorageCoreService.class);

	private Storage storage;

	@PostConstruct
	public void init() throws IOException {
		Credentials credentials = GoogleCredentials
				.fromStream(new FileInputStream("E:\\xtra\\_wolftechnica\\key\\springboot-tempemp3-72e44c75e603.json"));
		storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("springboot-tempemp3").build()
				.getService();
	}

	public void createBucket(String bucketName) {
		try {
			Bucket bucket = storage.create(BucketInfo.of(bucketName));
			log.info("Bucktet created succefully. : " + bucket.getName());
		} catch (Exception e) {
			log.error(e);
		}
	}

	public Bucket getBucket(String bucketName) {
		Bucket bucket = null;
		try {
			bucket = storage.get(bucketName);
			log.info(bucket);
			return bucket;
		} catch (Exception e) {
			log.error(e);
		}
		return bucket;
	}

	public void addFileToBucket(MultipartFile filePart, String bucketName) throws IOException {
		final String fileName = System.currentTimeMillis() + "-" + filePart.getOriginalFilename();
		@SuppressWarnings("deprecation")
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
				.setAcl(new ArrayList<>(
						Arrays.asList(Acl.of(User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER))))
				.build(), filePart.getInputStream());
		log.info(blobInfo);
	}

	public void addFileToBucket(MultipartFile filePart, String bucketName, String subDirectory, Long generation)
			throws IOException {
		final String fileName = System.currentTimeMillis() + "-" + filePart.getOriginalFilename();
		@SuppressWarnings("deprecation")
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, subDirectory + fileName, generation)
				.setAcl(new ArrayList<>(
						Arrays.asList(Acl.of(User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER))))
				.build(), filePart.getInputStream());
		log.info(blobInfo);
	}

	public Page<Blob> getObjectsFromBucket(String bucketName) throws GoogleException {
		Bucket bucket = getBucket(bucketName);
		if (bucket == null) {
			log.error("No bucket exist : " + bucketName);
			throw new GoogleException(GoogleExceptionCodes.NO_BUCKET_FOUND);
		}
		return bucket.list();
	}

	public Blob getObjectFromBucket(String bucketName, String blobName, long generation) throws GoogleException {
		Blob blob = getBucket(bucketName).get(blobName, BlobGetOption.generationMatch(generation));
		if (blob == null) {
			log.error("No bucket exist : " + bucketName);
			throw new GoogleException(GoogleExceptionCodes.NO_BUCKET_OBJECT_FOUND);
		}
		log.error("blob : " + blob.getGeneratedId());
		return blob;
	}

}
