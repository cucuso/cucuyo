package com.cucuyo.service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.cucuyo.dao.PropertyDao;
import com.cucuyo.dto.PropertiesDto;
import com.cucuyo.dto.SearchDto;
import com.cucuyo.model.Property;

import lombok.extern.slf4j.Slf4j;

@Slf4j()
public class PropertyService {

  PropertyDao propertyDao = new PropertyDao();

  public PropertiesDto getProperties(SearchDto search, String page) {
    log.info("Retrieving properties for searchDto <%s> and page <%s>", search, page);
    return propertyDao.getProperties(search, page);
  }

  public Property getProperty(String id) {
    return propertyDao.getProperty(id);
  }

  public Property writeProperty(Property property) {
    return propertyDao.saveProperty(property);
  }

  public void saveProperty(Property property) {
    uploadFileToS3("testImage"+ Math.random());
  }

  private static void uploadFileToS3(String name) {

    String bucketName = "cucuyo-images";
    String fileObjKeyName = name;

    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(Regions.US_EAST_2)
        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        .build();


    // Upload a file as a new object with ContentType and title specified.
    PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, new ByteArrayInputStream("dsfdf".getBytes(StandardCharsets.UTF_8)), null);
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("image");
    metadata.addUserMetadata("x-amz-meta-title", "property-image");
    request.setMetadata(metadata);
    s3Client.putObject(request);

  }

}
