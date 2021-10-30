package com.voxloud.provisioning.deviceservice;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voxloud.provisioning.constant.ProvisioningConstant;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.props.ProvisioningProps;
import com.voxloud.provisioning.utility.ProvisioningUtility;

/**
 * This class is responsible to configure Desk type of devices.
 * 
 * @author Hiren Savalia
 *
 */
@Service(value = "deskDeviceConfiguration")
public class DeskDeviceConfigurationImpl implements DeviceConfiguration {

	private static final Logger LOGGER = getLogger(DeskDeviceConfigurationImpl.class);

	@Autowired
	private ProvisioningProps provisioningProps;

	@Override
	public String generateDeviceConfigFile(Device device) {
		LOGGER.info("Configuring the device for macAddress :: {} of type :: {}", device.getMacAddress(),
				String.valueOf(device.getModel()));

		// 1. Create dataHashMap
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.putAll(ProvisioningUtility.getUserNamePasswordMap(device));
		responseMap.putAll(provisioningProps.getCommonPropsMap());

		// 2. Check for Override Fragment
		if (StringUtils.isNoneBlank(device.getOverrideFragment())) {
			String[] overrideFragmentArr = device.getOverrideFragment().split(ProvisioningConstant.NEWLINE);
			Map<String, Object> overrideFragmentMap = Stream.of(overrideFragmentArr)
					.map(s -> s.split(ProvisioningConstant.EQUALS))
					.collect(Collectors.toMap(x -> x[0], x -> x[1]));
			responseMap.putAll(overrideFragmentMap);
		}

		// 3. Convert to property file format
		return ProvisioningUtility.convertMaptoString(responseMap);
	}
}
