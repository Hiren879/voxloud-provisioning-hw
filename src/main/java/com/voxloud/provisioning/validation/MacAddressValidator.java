package com.voxloud.provisioning.validation;

import static org.slf4j.LoggerFactory.getLogger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.voxloud.provisioning.exception.BadInputException;

/**
 * This class is responsible to handle validations related to MacAddress.
 * 
 * @author Hiren Savalia
 *
 */

public class MacAddressValidator {

	private static final Logger LOGGER = getLogger(MacAddressValidator.class);

	public static void validateMacAddress(String macAddress) {
		// 1. check for null & empty
		LOGGER.info("Curently validating macAddress :: {}", macAddress);
		if (StringUtils.isAllBlank(macAddress)) {
			throw new BadInputException("Mac-Address is either null or empty.");
		}
		LOGGER.info("Validation is done for macAddress :: {}", macAddress);
	}
}
