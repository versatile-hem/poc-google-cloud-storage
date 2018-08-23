package com.wolftechnica.gc.storage.service;

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

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
@PropertySource(value = { "google.properties" }, ignoreResourceNotFound = true)
public class GoogleClouldStorageService {

	private Log log = LogFactory.getLog(GoogleClouldStorageService.class);

	private Storage storage;

	@PostConstruct
	public void init() throws FileNotFoundException, IOException {
		Credentials credentials = GoogleCredentials
				.fromStream(new FileInputStream("E:\\xtra\\_wolftechnica\\key\\springboot-tempemp3-72e44c75e603.json"));
		storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("springboot-tempemp3").build()
				.getService();
	}

	public void CreateBucket(String mobile) {
		try {
			Bucket bucket = storage.create(BucketInfo.of("tempemp" + mobile));
			log.info(bucket);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public Bucket getBucket(String mobile) {
		Bucket bucket =null;
		try {
			bucket = storage.get("tempemp"+mobile);
			log.info(bucket);
			return bucket;
		} catch (Exception e) {
			log.error(e);
		}
		return bucket;
	}

	public void addFileToBucket(MultipartFile filePart, String bucketName) throws IOException {
		final String fileName =  System.currentTimeMillis()+"-"+filePart.getOriginalFilename();
		@SuppressWarnings("deprecation")
		BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(bucketName, fileName)
				.setAcl(new ArrayList<>(
						Arrays.asList(Acl.of(User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER))))
				.build(), filePart.getInputStream());
		log.info(blobInfo);


		
	}

	/*
	 * 
	 * // private static final String BUCKET = //
	 * RemoteStorageHelper.generateBucketName(); private static final String
	 * USER_EMAIL = "dptechnologies17@gmail.com"; private static final String
	 * BLOB1 = "blob1"; private static final String BLOB2 = "blob2"; private
	 * static final String BLOB3 = "blob3"; private static final String BLOB4 =
	 * "blob4";
	 * 
	 * private static com.google.cloud.storage.Storage storage;
	 * 
	 * // @Autowired // private RestTemplate restTemplate;
	 * 
	 * @Autowired private Environment environment;
	 * 
	 * Log log = LogFactory.getLog(GoogleClouldStorageService.class);
	 * 
	 * @Test private void CreateBucket(String bucketName) {
	 * 
	 * StorageOptions.Builder optionsBuilder = StorageOptions.newBuilder();
	 * com.google.auth.Credentials credentials = new
	 * GoogleClouldStorageService(); optionsBuilder.setCredentials(credentials);
	 * 
	 * Storage storage = optionsBuilder.build().getService();
	 * 
	 * com.google.cloud.storage.Bucket bucket =
	 * storage.create(BucketInfo.newBuilder(bucketName)
	 * .setStorageClass(StorageClass.COLDLINE) .setLocation("asia").build()); }
	 * 
	 * @Test public void getBuckets() {
	 * 
	 * String url =
	 * "https://content.googleapis.com/storage/v1/b/tempemp9953503835/o?delimiter=,&includeTrailingDelimiter=true&projection=full&versions=true&key=AIzaSyA4rNKzrLRl9sDuBSW5yrfOAhlpl22XPig";
	 * RestTemplate restTemplate = new RestTemplate();
	 * org.springframework.http.HttpHeaders headers = new
	 * org.springframework.http.HttpHeaders();
	 * headers.setAccept(Arrays.asList(org.springframework.http.MediaType.
	 * APPLICATION_JSON)); headers.set("Authorization",
	 * "Bearer GmgABjUby93SY6LcL8Iqc9wMbIwFKt7s6x8");
	 * org.springframework.http.HttpEntity<String> entity = new
	 * org.springframework.http.HttpEntity<>("parameters", headers);
	 * 
	 * ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET,
	 * entity, String.class); System.out.println(res.getBody());
	 * 
	 * }
	 * 
	 * 
	 * { "kind": "storage#objects", "items": [ { "kind": "storage#object", "id":
	 * "tempemp9953503835/27858822_2047817491902147_1747705520866806571_n.jpg/1534789917632959",
	 * "selfLink":
	 * "https://www.googleapis.com/storage/v1/b/tempemp9953503835/o/27858822_2047817491902147_1747705520866806571_n.jpg",
	 * "name": "27858822_2047817491902147_1747705520866806571_n.jpg", "bucket":
	 * "tempemp9953503835", "generation": "1534789917632959", "metageneration":
	 * "1", "contentType": "image/jpeg", "timeCreated":
	 * "2018-08-20T18:31:57.632Z", "updated": "2018-08-20T18:31:57.632Z",
	 * "storageClass": "MULTI_REGIONAL", "timeStorageClassUpdated":
	 * "2018-08-20T18:31:57.632Z", "size": "61085", "md5Hash":
	 * "tpTUi9oeiYdwXVICvMhxdA==", "mediaLink":
	 * "https://www.googleapis.com/download/storage/v1/b/tempemp9953503835/o/27858822_2047817491902147_1747705520866806571_n.jpg?generation=1534789917632959&alt=media",
	 * "crc32c": "BRItmw==", "etag": "CL/T4f6h/NwCEAE=" } ] }
	 * 
	 * 
	 */}
