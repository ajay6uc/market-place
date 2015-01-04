package com.marketplace.service.amazon;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;


@Service
public class AmazonS3Service {

	private static Logger logger = LoggerFactory.getLogger("AmazonS3Service");
	
	//@Value("${s3AccessKeyId}")
	private String s3AccessKeyId="AKIAIM2LDH4AXKCNAYWA";
	
	//@Value("${s3SecretAccessKey}")
	private String s3SecretAccessKey="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";
	
	//@Value("${s3Bucket}")
	private String s3Bucket="futor-static-develop";
	
	BasicAWSCredentials awsCreds ;
	AmazonS3 amazonS3;
	Region asiaPacific = Region.getRegion(Regions.AP_NORTHEAST_1);

	
	
	/**
	 * @param org
	 * @param filePath
	 * @param contentType
	 * @param fileInputStream
	 * @throws IOException
	 */
	public void uploadPdfOrEpub(String org, String filePath, String contentType, InputStream fileInputStream) throws IOException {


		try {
			
			initializeS3();
			/*
			 * Upload an object to your bucket - You can easily upload a file to
			 * S3, or upload directly an InputStream if you know the length of
			 * the data in the stream. You can also specify your own metadata
			 * when uploading to S3, which allows you set a variety of options
			 * like content-type and content-encoding, plus additional metadata
			 * specific to your applications.
			 */
			logger.info("Uploading  object to S3 under key which will be same as filePath :" + filePath);
			ObjectMetadata metadata = new ObjectMetadata();
			//long contentLength = ((FileInputStream) fileInputStream).getChannel().size();
			logger.info("Uploading file having contentType  : " + contentType );
			metadata.setContentType(contentType);
		//	metadata.setContentLength(contentLength);
			amazonS3.putObject(new PutObjectRequest(s3Bucket, filePath, fileInputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));;

		} catch (AmazonServiceException ase) {
			String errorMessage = "Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon S3, but was rejected with an error response for some reason." 
					+ "Error Message:    " + ase.getMessage() 
					+ "HTTP Status Code: " + ase.getStatusCode()
					+ "AWS Error Code:   " + ase.getErrorCode()
					+ "Error Type:       " + ase.getErrorType()
					+ "Request ID:       " + ase.getRequestId();
			logger.error(errorMessage, ase);
			throw ase;
		} catch (AmazonClientException ace) {
			String errorMessage = "Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with S3 "
					+ "such as not being able to access the network";
			logger.error(errorMessage, ace);
			throw ace;
		} catch (Exception e) {
			logger.error("Error Ocurred while uploading image to amazon s3", e);
			throw e;
		}
	}
	
	

	/**
	 * @param org
	 * @param imagePath
	 * @param imageData
	 * @throws IOException
	 */
	public void uploadImage(String imagePath, byte [] imageData) throws IOException {


		try {
			
			initializeS3();
			/*
			 * Upload an object to your bucket - You can easily upload a file to
			 * S3, or upload directly an InputStream if you know the length of
			 * the data in the stream. You can also specify your own metadata
			 * when uploading to S3, which allows you set a variety of options
			 * like content-type and content-encoding, plus additional metadata
			 * specific to your applications.
			 */
			logger.info("Uploading image object to S3 under key which will be same as imagePath :" + imagePath);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
			ObjectMetadata metadata = new ObjectMetadata();
			String contentType = "image/" + imagePath.substring(imagePath.lastIndexOf('.') + 1);
			logger.info("Uploading image having contentType  : " + contentType + " and having size : " + imageData.length);
			metadata.setContentType(contentType);
			metadata.setContentLength(imageData.length);
			amazonS3.putObject(new PutObjectRequest(s3Bucket, imagePath, byteArrayInputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));;

		} catch (AmazonServiceException ase) {
			String errorMessage = "Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon S3, but was rejected with an error response for some reason." 
					+ "Error Message:    " + ase.getMessage() 
					+ "HTTP Status Code: " + ase.getStatusCode()
					+ "AWS Error Code:   " + ase.getErrorCode()
					+ "Error Type:       " + ase.getErrorType()
					+ "Request ID:       " + ase.getRequestId();
			logger.error(errorMessage, ase);
			throw ase;
		} catch (AmazonClientException ace) {
			String errorMessage = "Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with S3 "
					+ "such as not being able to access the network";
			logger.error(errorMessage, ace);
			throw ace;
		} catch (Exception e) {
			logger.error("Error Ocurred while uploading image to amazon s3", e);
			throw e;
		}
	}
	

	/**
	 * Initializes s3 resources 
	 * 1. Authentication mechanism BasicAWSCredentials
	 * 2. creates AmazonS3Client to connect to amazon s3
	 * 3. Set region for s3client
	 * 4. Creates amazon bucket only if its required
	 */
	private void initializeS3(){
		
		if(awsCreds== null){
			awsCreds = new BasicAWSCredentials(s3AccessKeyId, s3SecretAccessKey);
		}
		if(amazonS3== null){
			amazonS3 = new AmazonS3Client(awsCreds);
		}	
		amazonS3.setRegion(asiaPacific);
		/*
		 * Create a new S3 bucket - Amazon S3 bucket names are globally unique,
		 * so once a bucket name has been taken by any user, you can't create
		 * another bucket with that same name.
		 * creates only if bucket does not exists
		 */
		if(s3Bucket !=null && !(amazonS3.doesBucketExist(s3Bucket)))
		{
			amazonS3.createBucket(s3Bucket);
		}	 

	}

}
