package com.voxloud.provisioning.deviceservice;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.provisioning.constant.ProvisioningConstant;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.GenericException;
import com.voxloud.provisioning.props.ProvisioningProps;
import com.voxloud.provisioning.utility.ProvisioningUtility;

/**
 * This class is responsible to configure Conference type of devices.
 * 
 * @author Hiren Savalia
 *
 */
@Service(value = "conferenceDeviceConfiguration")
public class ConferenceDeviceConfigurationImpl implements DeviceConfiguration {

	private static final Logger LOGGER = getLogger(ConferenceDeviceConfigurationImpl.class);

	@Autowired
	private ProvisioningProps provisioningProps;

	@Override
	public String generateDeviceConfigFile(Device device) {
		String macAddress = device.getMacAddress();
		LOGGER.info("Configuring the device for macAddress :: {} of type :: {}", macAddress,
				String.valueOf(device.getModel()));
		// 1. Get the userName-password &common props
		Map<String, Object> responseMap = ProvisioningUtility.getUserNamePasswordMap(device);
		responseMap.putAll(provisioningProps.getCommonPropsMap());

		// 2. Get the codecs & convert it into List and replace it in Map
		List<String> codesList = Stream.of(
									responseMap.get(ProvisioningConstant.CODECS)
									.toString()
									.split(","))
									.collect(Collectors.toList());
		responseMap.put(ProvisioningConstant.CODECS, codesList);

		// 3. Check for the Override fragment
		if (StringUtils.isNoneBlank(device.getOverrideFragment())) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				LOGGER.info("Converting fragments into map for macAddress :: {}", macAddress);
				Map<String, Object> overrideFragmentMap = mapper.readValue(device.getOverrideFragment(),
						new TypeReference<Map<String, Object>>() {
						});
				responseMap.putAll(overrideFragmentMap);
			} catch (JsonProcessingException e) {
				LOGGER.error("Exception occurred while converting data into objectMapper.", e);
				throw new GenericException(e.getMessage());
			}
		}
		return ProvisioningUtility.convertMapToJsonFile(responseMap);
	}
}
