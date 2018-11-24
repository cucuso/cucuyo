package com.cucuyo.dto;

import java.util.List;

import lombok.Data;

@Data()
public class CreateRequest {

  private String address;
  private String description;
  private String phone;
  private Double price;
  private Float longitude;
  private Float latitude;
  private List<String> images;

}
