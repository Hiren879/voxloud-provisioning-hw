package com.voxloud.provisioning.utility;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.provisioning.constant.ProvisioningConstant;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.exception.GenericException;

/**
 * This class is responsible to handle utility methods.
 * 
 * @author Hiren Savalia
 *
 */

public class ProvisioningUtility {

	private static final Logger LOGGER = getLogger(ProvisioningUtility.class);
	
	/**
	 * This method will return map containing user-name & password for given device.
	 * 
	 * @param device
	 * @return
	 */
	public static Map<String, Object> getUserNamePasswordMap(Device device) {
		Map<String, Object> userNamePasswordMap = new HashMap<>();
		userNamePasswordMap.put(ProvisioningConstant.USERNAME, device.getUsername());
		userNamePasswordMap.put(ProvisioningConstant.PASSWORD, device.getPassword());
		return userNamePasswordMap;
	}
	
	/**
	 * This method will covert given map to property file format
	 * @param map
	 * @return
	 */
	public static String convertMaptoString(Map<String, Object> map) {
		StringBuilder responseString = new StringBuilder();

		map.entrySet()
			.forEach(e -> responseString
					.append(e.getKey())
					.append(ProvisioningConstant.EQUALS)
					.append(e.getValue())
					.append(System.lineSeparator()));
		
		return responseString.toString();
	}
	
	/**
	 * This method will convert given map to JSON format
	 * @param map
	 * @return
	 */
	public static String convertMapToJsonFile(Map<String, Object> map) {
		String responseStr = new String();
		ObjectMapper mapper = new ObjectMapper();
		try {
			responseStr = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			LOGGER.error("Exception occured while converting map to json", e);
			throw new GenericException(e.getMessage());
		}
		return responseStr;
	}
}
