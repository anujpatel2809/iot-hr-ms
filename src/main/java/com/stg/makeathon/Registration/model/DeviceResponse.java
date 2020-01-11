package com.stg.makeathon.Registration.model;

import lombok.Data;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Data
public class DeviceResponse {

  private String hardwareId;
  private String tbDeviceId;
  private String accessToken;
  private Boolean status;
  private String type;
  private String name;

}
