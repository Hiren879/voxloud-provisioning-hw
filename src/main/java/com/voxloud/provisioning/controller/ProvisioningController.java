package com.voxloud.provisioning.controller;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voxloud.provisioning.service.ProvisioningService;

/**
 * This class is responsible to handle the incoming provisioning requests.
 * 
 * @author Hiren Savalia
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ProvisioningController {

	private static final Logger LOGGER = getLogger(ProvisioningController.class);

	@Autowired
	private ProvisioningService provisioningService;

	@GetMapping("/provisioning/{macAddress}")
	public ResponseEntity<String> getProvisioning(@PathVariable("macAddress") String macAddress) {
		LOGGER.info("Entering provisioning system with macAddress :: {}", macAddress);
		String response = provisioningService.getProvisioningFile(macAddress);
		LOGGER.info("Returning from provisioning system for macAddress :: {} with OK response.", macAddress);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}