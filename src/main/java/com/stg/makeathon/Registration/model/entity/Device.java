package com.stg.makeathon.Registration.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Entity
@Data
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String hardwareId;
  private String tbDeviceId;
  private String accessToken;
  private Boolean status;
  private String type;
  private String label;
  private String name;
  private String serviceUserName;
  private String servicePassword;
  private String entityType;
  private String tenantId;
}
