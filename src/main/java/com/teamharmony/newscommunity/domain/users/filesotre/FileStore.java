package com.teamharmony.newscommunity.domain.users.filesotre;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class FileStore {
	private final AmazonS3Client s3;
	
	public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		optionalMetadata.ifPresent(map -> { if (!map.isEmpty()) {
			map.forEach(metadata::addUserMetadata);
		} });
		try {
			s3.putObject(new PutObjectRequest(path, fileName, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (AmazonServiceException e) {
			throw new IllegalStateException("Failed to store file to s3", e);
		}
	}
	
	public void delete(String path, String fileName) throws IOException {
		try { s3.deleteObject(path, fileName); } catch (AmazonServiceException e) { throw new IllegalStateException("Failed to delete file to s3", e);}
	}
	
	public String download(String path, String key) {
		try {
			return s3.getResourceUrl(path, key);
		} catch (AmazonServiceException e) {
			throw  new IllegalStateException("Failed to download file from s3", e);
		}
	}
}
