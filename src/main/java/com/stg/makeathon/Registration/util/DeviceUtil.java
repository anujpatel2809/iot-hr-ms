package com.stg.makeathon.Registration.util;

import com.stg.makeathon.Registration.model.DeviceRequest;
import com.stg.makeathon.Registration.model.DeviceResponse;
import com.stg.makeathon.Registration.model.TenantId;
import com.stg.makeathon.Registration.model.ThingsBoardDeviceRequest;
import com.stg.makeathon.Registration.model.entity.Device;
import org.springframework.stereotype.Component;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Component
public class DeviceUtil {

  public DeviceResponse mapDeviceToDeviceReponse(Device device) {
    DeviceResponse deviceResponse = new DeviceResponse();
    deviceResponse.setAccessToken(device.getAccessToken());
    deviceResponse.setHardwareId(device.getHardwareId());
    deviceResponse.setName(device.getName());
    deviceResponse.setStatus(device.getStatus());
    deviceResponse.setType(device.getType());
    deviceResponse.setTbDeviceId(device.getTbDeviceId());
    return deviceResponse;
  }

  public Device mapDeviceRequestToDevice(DeviceRequest deviceRequest) {
    Device device = new Device();
    device.setStatus(deviceRequest.getStatus());
    device.setType(deviceRequest.getType());
    device.setHardwareId(deviceRequest.getHardwareId());
    device.setEntityType(deviceRequest.getEntityType());
    device.setLabel(deviceRequest.getLabel());
    device.setName(deviceRequest.getName());
    device.setServicePassword(deviceRequest.getServicePassword());
    device.setServiceUserName(deviceRequest.getServiceUserName());
    device.setTenantId(deviceRequest.getTenantId());
    return device;
  }

  public ThingsBoardDeviceRequest mapDeviceToThingsBoardDeviceRequest(Device device) {
    ThingsBoardDeviceRequest request = new ThingsBoardDeviceRequest();
    request.setLabel(device.getLabel());
    request.setName(device.getName());
    request.setType(device.getType());
    TenantId tenantId = new TenantId();
    tenantId.setEntityType(device.getEntityType());
    tenantId.setId(device.getTenantId());
    request.setTenantId(tenantId);
    return request;
  }

  public String getAccessToken() {
    String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    int count = 10;
    StringBuilder builder = new StringBuilder();
    while (count-- != 0) {
      int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
      builder.append(ALPHA_NUMERIC_STRING.charAt(character));
    }
    return builder.toString();
  }
}
