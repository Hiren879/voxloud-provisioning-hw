package com.voxloud.provisioning.deviceservice;

import com.voxloud.provisioning.entity.Device;

public interface DeviceConfiguration {
	
	public String generateDeviceConfigFile(Device device);

}
