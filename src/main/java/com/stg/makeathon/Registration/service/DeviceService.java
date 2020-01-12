package com.stg.makeathon.Registration.service;

import com.stg.makeathon.Registration.model.DeviceRequest;
import com.stg.makeathon.Registration.model.DeviceResponse;
import java.util.List;
import javassist.NotFoundException;

/**
 * Created by manish.bisht on 1/11/2020.
 */
public interface DeviceService {

  DeviceResponse getToken(String hardwareId) throws NotFoundException;

  void addDevice(List<DeviceRequest> deviceRequests);

  DeviceResponse update(DeviceRequest deviceRequest);

  void deleteDevice(String hardwareId);

  DeviceResponse clearToken(DeviceRequest deviceRequest);
}
