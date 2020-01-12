package com.stg.makeathon.Registration.service;

import com.stg.makeathon.Registration.model.DeviceRequest;
import com.stg.makeathon.Registration.model.DeviceResponse;
import com.stg.makeathon.Registration.model.ThingsBoardDeviceRequest;
import com.stg.makeathon.Registration.model.ThingsBoardDeviceResponse;
import com.stg.makeathon.Registration.model.ThingsBoardLogin;
import com.stg.makeathon.Registration.model.ThingsBoardToken;
import com.stg.makeathon.Registration.model.entity.Device;
import com.stg.makeathon.Registration.repository.DeviceRepository;
import com.stg.makeathon.Registration.util.DeviceUtil;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javassist.NotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

  private final DeviceRepository deviceRepository;
  private final RestTemplate restTemplate;
  private final DeviceUtil deviceUtil;

  @Value("${thingsBoard.login.url:http://demo.thingsboard.io/api/auth/login}")
  private String thingsBoardLoginUrl;

  @Value("${thingsBoard.device.url:https://demo.thingsboard.io/api/device}")
  private String thingsBoardDeviceUrl;

  public DeviceServiceImpl(DeviceRepository deviceRepository, RestTemplate restTemplate,
      DeviceUtil deviceUtil) {
    this.deviceRepository = deviceRepository;
    this.restTemplate = restTemplate;
    this.deviceUtil = deviceUtil;
  }

  @Override
  public DeviceResponse getToken(String hardwareId) throws NotFoundException {
    Device device = deviceRepository.findDeviceByHardwareId(hardwareId);
    if (null == device) {
      throw new NotFoundException("Devices Not Found");
    }

    if (!device.getStatus()) {
      throw new IllegalAccessError();
    }

    if (!Strings.isBlank(device.getAccessToken())) {
      return deviceUtil.mapDeviceToDeviceReponse(device);
    }

    return addToPlatform(device);
  }

  @Override
  public void addDevice(List<DeviceRequest> deviceRequests) {
    for (DeviceRequest deviceRequest : deviceRequests) {
      Device device = deviceUtil.mapDeviceRequestToDevice(deviceRequest);
      deviceRepository.save(device);
    }
  }

  @Override
  public DeviceResponse update(DeviceRequest deviceRequest) {
    Device device = deviceRepository.findDeviceByHardwareId(deviceRequest.getHardwareId());
    deviceUtil.mapDeviceRequestToDevice(deviceRequest, device);
    deviceRepository.save(device);
    return deviceUtil.mapDeviceToDeviceReponse(device);
  }

  @Override
  public void deleteDevice(String hardwareId) {
    Device device = deviceRepository.findDeviceByHardwareId(hardwareId);
    if (!Strings.isBlank(device.getAccessToken()) && (device.getSource() == null || device
        .getSource().equals("thboard"))) {
      deleteFromThingsBoard(device);
    }
    deviceRepository.delete(device);
  }

  @Override
  public DeviceResponse clearToken(DeviceRequest deviceRequest) {
    Device device = deviceRepository.findDeviceByHardwareId(deviceRequest.getHardwareId());
    if (!Strings.isBlank(device.getAccessToken()) && (device.getSource() == null || device
        .getSource().equals("thboard"))) {
      deleteFromThingsBoard(device);
    }
    device.setAccessToken(null);
    device.setTbDeviceId(null);
    return deviceUtil.mapDeviceToDeviceReponse(deviceRepository.save(device));
  }

  private void deleteFromThingsBoard(Device device) {
    ThingsBoardLogin thingsBoardLogin = new ThingsBoardLogin();
    thingsBoardLogin.setPassword(device.getServicePassword());
    thingsBoardLogin.setUsername(device.getServiceUserName());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-Authorization", "Bearer " + getToken(thingsBoardLogin));

    ThingsBoardDeviceRequest request = deviceUtil.mapDeviceToThingsBoardDeviceRequest(device);

    HttpEntity<ThingsBoardDeviceRequest> entity = new HttpEntity<>(request, headers);
    String url = thingsBoardDeviceUrl + "/" + device.getTbDeviceId();
    restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
  }

  private DeviceResponse addToPlatform(Device device) {
    if (device.getSource() == null || device.getSource().equals("thboard")) {
      device.setSource("thboard");
      return addToThingsBoard(device);
    }
    device.setSource("openPlatform");
    return addToOpenPlatform(device);
  }

  private DeviceResponse addToOpenPlatform(Device device) {
    String accessToken = deviceUtil.getAccessToken();
    String deviceId = UUID.randomUUID().toString();
    device.setTbDeviceId(deviceId);
    device.setAccessToken(accessToken);
    return deviceUtil.mapDeviceToDeviceReponse(deviceRepository.save(device));
  }

  private DeviceResponse addToThingsBoard(Device device) {
    ThingsBoardLogin thingsBoardLogin = new ThingsBoardLogin();
    thingsBoardLogin.setPassword(device.getServicePassword());
    thingsBoardLogin.setUsername(device.getServiceUserName());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-Authorization", "Bearer " + getToken(thingsBoardLogin));

    ThingsBoardDeviceRequest request = deviceUtil.mapDeviceToThingsBoardDeviceRequest(device);

    HttpEntity<ThingsBoardDeviceRequest> entity = new HttpEntity<>(request, headers);
    String accessToken = deviceUtil.getAccessToken();
    String url = thingsBoardDeviceUrl + "?accessToken=" + accessToken;
    ThingsBoardDeviceResponse responseEntity = restTemplate
        .postForObject(url, entity, ThingsBoardDeviceResponse.class);
    if (null != responseEntity) {
      device.setTbDeviceId(responseEntity.getId().getId());
      device.setAccessToken(accessToken);
      deviceRepository.save(device);
    }
    DeviceResponse response = deviceUtil.mapDeviceToDeviceReponse(device);
    return response;
  }

  private String getToken(ThingsBoardLogin thingsBoardLogin) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<ThingsBoardLogin> entity = new HttpEntity<>(thingsBoardLogin, headers);
    ThingsBoardToken token = restTemplate
        .postForObject(thingsBoardLoginUrl, entity, ThingsBoardToken.class);
    return token.getToken();
  }
}
