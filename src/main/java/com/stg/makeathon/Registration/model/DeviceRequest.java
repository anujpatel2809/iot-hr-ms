package com.stg.makeathon.Registration.model;

import lombok.Data;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Data
public class DeviceRequest {

  private String hardwareId;
  private String label;
  private String name;
  private String type;
  private Boolean status;
  private String serviceUserName;
  private String servicePassword;
  private String entityType;
  private String tenantId;
  private String source;
}
