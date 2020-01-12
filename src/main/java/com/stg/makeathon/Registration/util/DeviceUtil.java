package com.stg.makeathon.Registration.util;

import com.stg.makeathon.Registration.model.DeviceRequest;
import com.stg.makeathon.Registration.model.DeviceResponse;
import com.stg.makeathon.Registration.model.TenantId;
import com.stg.makeathon.Registration.model.ThingsBoardDeviceRequest;
import com.stg.makeathon.Registration.model.entity.Device;
import org.apache.logging.log4j.util.Strings;
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
    deviceResponse.setSource(device.getSource());
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
    device.setSource(deviceRequest.getSource());
    return device;
  }

  public void mapDeviceRequestToDevice(DeviceRequest deviceRequest, Device device) {

    if (null != deviceRequest.getStatus() && !device.getStatus()
        .equals(deviceRequest.getStatus())) {
      device.setStatus(deviceRequest.getStatus());
    }
    if (Strings.isBlank(deviceRequest.getType()) && !device.getType()
        .equals(deviceRequest.getType())) {
      device.setType(deviceRequest.getType());
    }
    if (Strings.isBlank(deviceRequest.getEntityType()) && !device.getEntityType()
        .equals(deviceRequest.getEntityType())) {
      device.setEntityType(deviceRequest.getEntityType());
    }
    if (Strings.isBlank(deviceRequest.getLabel()) && !device.getLabel()
        .equals(deviceRequest.getLabel())) {
      device.setLabel(deviceRequest.getLabel());
    }
    if (Strings.isBlank(deviceRequest.getName()) && !device.getName()
        .equals(deviceRequest.getName())) {
      device.setName(deviceRequest.getName());
    }
    if (Strings.isBlank(deviceRequest.getServicePassword()) && !device.getServicePassword()
        .equals(deviceRequest.getServicePassword())) {
      device.setServicePassword(deviceRequest.getServicePassword());
    }
    if (Strings.isBlank(deviceRequest.getServiceUserName()) && !device.getServiceUserName()
        .equals(deviceRequest.getServiceUserName())) {
      device.setServiceUserName(deviceRequest.getServiceUserName());
    }
    if (Strings.isBlank(deviceRequest.getTenantId()) && !device.getTenantId()
        .equals(deviceRequest.getTenantId())) {
      device.setTenantId(deviceRequest.getTenantId());
    }
    if (Strings.isBlank(deviceRequest.getSource()) && !device.getSource()
        .equals(deviceRequest.getSource())) {
      device.setSource(deviceRequest.getSource());
    }
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
