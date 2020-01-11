package com.stg.makeathon.Registration.model;

import lombok.Data;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Data
public class ThingsBoardDeviceRequest {

  private String label;
  private String name;
  private TenantId tenantId;
  private String type;
}
