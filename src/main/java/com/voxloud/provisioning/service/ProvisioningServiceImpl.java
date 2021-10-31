package com.voxloud.provisioning.service;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.voxloud.provisioning.deviceservice.DeviceConfiguration;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.enums.DeviceModel;
import com.voxloud.provisioning.exception.DeviceTypeNotSupportedException;
import com.voxloud.provisioning.exception.ResourceNotFoundException;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.validation.MacAddressValidator;

/**
 * This class will act as service-orchestration layer for incoming macAddress.
 * 
 * @author Hiren Savalia
 *
 */

@Service
public class ProvisioningServiceImpl implements ProvisioningService {

	private static final Logger LOGGER = getLogger(ProvisioningServiceImpl.class);

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	@Qualifier("conferenceDeviceConfiguration")
	private DeviceConfiguration conferenceDeviceConfiguration;

	@Autowired
	@Qualifier("deskDeviceConfiguration")
	private DeviceConfiguration deskDeviceConfiguration;

	public String getProvisioningFile(String macAddress) {

		// 1. Validate incoming parameters
		MacAddressValidator.validateMacAddress(macAddress);

		// 2. Fetch data from DB for given macAddress or throw exception
		Device device = deviceRepository.findById(macAddress).orElseThrow(
				() -> new ResourceNotFoundException("Device is not found with mac-address : " + macAddress));

		// 3. Call service based on Device Type
		DeviceModel deviceModel = device.getModel();
		LOGGER.info("DeviceType for macAddress :: {} found is :: {}", macAddress, String.valueOf(deviceModel));

		if (device.getModel() != null && deviceModel == DeviceModel.DESK) {
			// 3.1 : Desk Config call
			LOGGER.info("Calling desk device config for macAddress :: {}", macAddress);
			return deskDeviceConfiguration.generateDeviceConfigFile(device);
		} else if (device.getModel() != null && deviceModel == DeviceModel.CONFERENCE) {
			// 3.2 : Conference Config call
			LOGGER.info("Calling conference device config for macAddress :: {}", macAddress);
			return conferenceDeviceConfiguration.generateDeviceConfigFile(device);
		} else {
			throw new DeviceTypeNotSupportedException(
					"Contact Support !! Currently this device type is not supported => "
							+ String.valueOf(device.getModel()));
		}
	}
}
