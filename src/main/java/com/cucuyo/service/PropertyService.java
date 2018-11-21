package com.cucuyo.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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

  public PropertiesDto getProperties(SearchDto search) {
    log.info("Retrieving properties for searchDto {} and page {}", search);
    return propertyDao.getProperties(search);
  }

  public Property getProperty(String id) {
    return propertyDao.getProperty(id);
  }

  public Property writeProperty(Property property) {
    return propertyDao.saveProperty(property);
  }

  public void saveProperty(String address, String description, Double price, List<String> images) {

    Long propertyId = System.currentTimeMillis() - 1542810186703L;

    List<String> dbImages = new ArrayList<>();
    int i = 1;
    for (String img : images) {

      String imageType = img.split(",")[0];
      String imageData = img.split(",")[1];

      String imgName = String.format("%s_%s.%s", propertyId, i, imageType);
      uploadFileToS3(imgName, imageType, imageData);
      dbImages.add(imgName);
      i++;

    }

    Property property = new Property();

    property.setAddress(address);
    property.setDescription(description);
    property.setPrice(price);
    property.setId(System.currentTimeMillis() - 1542810186703L);
    property.setImages(dbImages);
    propertyDao.saveProperty(property);

  }

  private void uploadFileToS3(String filename, String imageType, String imageData) {

    String bucketName = "cucuyo-images";

    AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(Regions.US_EAST_2)
        .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
        .build();

    // Upload a file as a new object with ContentType and title specified.
    PutObjectRequest request =
        new PutObjectRequest(bucketName, filename, getByteStream(imageData), getMetaData(imageType));
    request.setCannedAcl(CannedAccessControlList.PublicRead);
    s3Client.putObject(request);

  }

  private static InputStream getByteStream(String in) {
    byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(in.getBytes());
    return new ByteArrayInputStream(decoded);
  }

  private ObjectMetadata getMetaData(String imageType) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(imageType);
    metadata.addUserMetadata("x-amz-meta-title", "property-image");
    return metadata;
  }
}
