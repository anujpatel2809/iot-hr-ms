package com.stg.makeathon.Registration.controller;

import com.stg.makeathon.Registration.model.DeviceRequest;
import com.stg.makeathon.Registration.model.DeviceResponse;
import com.stg.makeathon.Registration.service.DeviceService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@RestController
@RequestMapping("/registration/device")
public class DeviceController {

  private final DeviceService deviceService;

  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @GetMapping("/{assetId}")
  public DeviceResponse getToken(@PathVariable("assetId") String hardwareId,
      @RequestHeader(value = "source", required = true) String optionalHeader)
      throws Exception {
    System.out.println("Request coming");
    if (null == optionalHeader || !optionalHeader.equals("gateway")) {
      throw new Exception("Bad Request");
    }
    return deviceService.getToken(hardwareId);
  }

  @PostMapping
  public void addDevice(@RequestBody List<DeviceRequest> deviceRequests) {
    deviceService.addDevice(deviceRequests);
  }

  @PostMapping("/clearToken")
  public DeviceResponse clearToken(@RequestBody DeviceRequest deviceRequest) {
    return deviceService.clearToken(deviceRequest);
  }

  @PutMapping
  public DeviceResponse upDateDevice(@RequestBody DeviceRequest deviceRequest) {
    return deviceService.update(deviceRequest);
  }

  @DeleteMapping("/{assetId}")
  public void deleteDevice(@PathVariable("assetId") String hardwareId) {
    deviceService.deleteDevice(hardwareId);
  }
}
