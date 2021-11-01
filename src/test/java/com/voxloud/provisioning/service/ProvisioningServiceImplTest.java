package com.voxloud.provisioning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.voxloud.provisioning.deviceservice.DeviceConfiguration;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.enums.DeviceModel;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.validation.MacAddressValidator;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProvisioningServiceImpl.class, MacAddressValidator.class })
public class ProvisioningServiceImplTest {

	private static final String VALIDATE_MAC_ADDRESS = "validateMacAddress";
	private static final String SOME_VALID_RESPONSE = "Some-valid-response";
	private static final String AA_BB_CC_DD_EE_FF = "aa-bb-cc-dd-ee-ff";
	private static final String PASSWORD = "password";
	private static final String USER_NAME = "UserName";

	@InjectMocks
	private ProvisioningServiceImpl provisioningServiceImpl;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock(name = "deskDeviceConfiguration")
	private DeviceConfiguration deskDeviceConfiguration;

	@Before
	public void setUp() {
		PowerMockito.mockStatic(MacAddressValidator.class);
	}

	@Test
	public void test_getProvisioningFile() throws Exception {
		// Prepare
		String macAddress = AA_BB_CC_DD_EE_FF;
		Optional<Device> device = prepareDevice();

		// Argument Captor
		ArgumentCaptor<String> macAddressCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Device> deviceCaptor = ArgumentCaptor.forClass(Device.class);
		ArgumentCaptor<String> macAddressValidatorCaptor = ArgumentCaptor.forClass(String.class);

		// Mock
		PowerMockito.doNothing().when(MacAddressValidator.class, VALIDATE_MAC_ADDRESS,
				macAddressValidatorCaptor.capture());
		Mockito.when(deviceRepository.findById(Mockito.anyString())).thenReturn(device);
		Mockito.when(deskDeviceConfiguration.generateDeviceConfigFile(device.get())).thenReturn(SOME_VALID_RESPONSE);

		// Call
		String response = provisioningServiceImpl.getProvisioningFile(macAddress);

		// Verify the call & capture the arguments
		Mockito.verify(deviceRepository, times(1)).findById(macAddressCaptor.capture());
		Mockito.verify(deskDeviceConfiguration, times(1)).generateDeviceConfigFile(deviceCaptor.capture());
		PowerMockito.verifyStatic(MacAddressValidator.class);
		MacAddressValidator.validateMacAddress(Mockito.anyString());

		// Verify the order
		InOrder order = Mockito.inOrder(deviceRepository, deskDeviceConfiguration);
		order.verify(deviceRepository).findById(Mockito.anyString());
		order.verify(deskDeviceConfiguration).generateDeviceConfigFile(Mockito.any(Device.class));

		// Assert
		assertEquals(response, SOME_VALID_RESPONSE);
		assertEquals(macAddress, macAddressCaptor.getValue());
		assertEquals(macAddress, device.get().getMacAddress());
		assertEquals(DeviceModel.DESK, device.get().getModel());
		assertEquals(USER_NAME, device.get().getUsername());
		assertEquals(PASSWORD, device.get().getPassword());

	}

	private Optional<Device> prepareDevice() {
		Device device = new Device();
		device.setMacAddress(AA_BB_CC_DD_EE_FF);
		device.setModel(DeviceModel.DESK);
		device.setOverrideFragment(null);
		device.setPassword(PASSWORD);
		device.setUsername(USER_NAME);
		return Optional.of(device);
	}
}
