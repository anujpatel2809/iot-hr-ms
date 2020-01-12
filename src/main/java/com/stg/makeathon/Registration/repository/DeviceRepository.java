package com.stg.makeathon.Registration.repository;

import com.stg.makeathon.Registration.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by manish.bisht on 1/11/2020.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

  Device findDeviceByHardwareId(String hardwareId);

}
