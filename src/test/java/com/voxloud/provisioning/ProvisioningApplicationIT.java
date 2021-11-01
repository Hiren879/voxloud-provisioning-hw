package com.voxloud.provisioning;

import static org.junit.Assert.assertEquals;

import org.json.JSONException;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.voxloud.provisioning.model.ExceptionResponse;

/**
 * This Integration Test Case class is responsible to test all possible
 * scenarios for Provisioning application.
 * 
 * APPLICATION MUST BE UP AND RUNNING BEFORE RUNNING THIS TEST CLASS.
 * 
 * @author Hiren Savalia
 *
 */
@SpringBootTest
public class ProvisioningApplicationIT {

	private static final String HTTP_LOCALHOST_8080_API_V1_PROVISIONING_SOME_WRONG_MAC_ADDRESS = "http://localhost:8080/api/v1/provisioning/some-wrong-mac-address";
	private static final String HTTP_LOCALHOST_8080_API_V1_PROVISIONING_1A_2B_3C_4D_5E_6F = "http://localhost:8080/api/v1/provisioning/1a-2b-3c-4d-5e-6f";
	private static final String HTTP_LOCALHOST_8080_API_V1_PROVISIONING_A1_B2_C3_D4_E5_F6 = "http://localhost:8080/api/v1/provisioning/a1-b2-c3-d4-e5-f6";
	private static final String HTTP_LOCALHOST_8080_API_V1_PROVISIONING_F1_E2_D3_C4_B5_A6 = "http://localhost:8080/api/v1/provisioning/f1-e2-d3-c4-b5-a6";
	private static final String HTTP_LOCALHOST_8080_API_V1_PROVISIONING_AA_BB_CC_DD_EE_FF = "http://localhost:8080/api/v1/provisioning/aa-bb-cc-dd-ee-ff";
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	@DisplayName("Positive TestCase Scenario: Device : Desk. Override Fragment is not available.")
	public void test_getProvisioning_Desk() throws JSONException {
		// Prepare
		String expected = "password=doe\r\n" + "port=5060\r\n" + "username=john\r\n" + "domain=sip.voxloud.com\r\n"
				+ "codecs=G711,G729,OPUS\r\n";

		// Call
		ResponseEntity<String> response = restTemplate
				.getForEntity(HTTP_LOCALHOST_8080_API_V1_PROVISIONING_AA_BB_CC_DD_EE_FF, String.class);
		String responseBody = response.getBody();

		// Assert
		assertEquals(expected, responseBody);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());

	}

	@Test
	@DisplayName("Positive TestCase Scenario: Device : Conference. Override Fragment is not available.")
	public void test_getProvisioning_Conference() throws JSONException {
		// Prepare
		String expected = "{\"password\":\"red\",\"port\":\"5060\",\"domain\":\"sip.voxloud.com\",\"codecs\":[\"G711\",\"G729\",\"OPUS\"],\"username\":\"sofia\"}";

		// Call
		ResponseEntity<String> response = restTemplate
				.getForEntity(HTTP_LOCALHOST_8080_API_V1_PROVISIONING_F1_E2_D3_C4_B5_A6, String.class);
		String responseBody = response.getBody();

		// Assert
		assertEquals(expected, responseBody);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	@DisplayName("Positive TestCase Scenario: Device : Desk. Override Fragment is available.")
	public void test_getProvisioning_Desk_With_Fragment() throws JSONException {
		// Prepare
		String expected = "password=white\r\n" + "port=5161\r\n" + "timeout=10\r\n" + "username=walter\r\n"
				+ "domain=sip.anotherdomain.com\r\n" + "codecs=G711,G729,OPUS\r\n" + "";

		// Call
		ResponseEntity<String> response = restTemplate
				.getForEntity(HTTP_LOCALHOST_8080_API_V1_PROVISIONING_A1_B2_C3_D4_E5_F6, String.class);
		String responseBody = response.getBody();

		// Assert
		assertEquals(expected, responseBody);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());

	}

	@Test
	@DisplayName("Positive TestCase Scenario: Device : Conference. Override Fragment is not available.")
	public void test_getProvisioning_Conference_With_Fragment() throws JSONException {
		// Prepare
		String expected = "{\"password\":\"blue\",\"port\":\"5161\",\"domain\":\"sip.anotherdomain.com\",\"codecs\":[\"G711\",\"G729\",\"OPUS\"],\"timeout\":10,\"username\":\"eric\"}";

		// Call
		ResponseEntity<String> response = restTemplate
				.getForEntity(HTTP_LOCALHOST_8080_API_V1_PROVISIONING_1A_2B_3C_4D_5E_6F, String.class);
		String responseBody = response.getBody();

		// Assert
		assertEquals(expected, responseBody);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	@DisplayName("Negative TestCase Scenario: Wrong MacAddress is supplied.")
	public void test_getProvisioning_Desk_Wrong_MacAddress() throws JSONException {
		// Prepare
		ExceptionResponse expected = prepareExceptionRespinseForWrongMacAddress();

		// Call
		ResponseEntity<ExceptionResponse> response = restTemplate
				.getForEntity(HTTP_LOCALHOST_8080_API_V1_PROVISIONING_SOME_WRONG_MAC_ADDRESS, ExceptionResponse.class);

		String errMsg = response.getBody().getErrMessage();
		HttpStatus statusCode = response.getStatusCode();
		String responseUrl = response.getBody().getUrl();

		// Assert
		assertEquals(expected.getErrMessage(), errMsg);
		assertEquals(expected.getStatusCode(), statusCode);
		assertEquals(expected.getUrl(), responseUrl);

	}

	private ExceptionResponse prepareExceptionRespinseForWrongMacAddress() {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setErrMessage("Device is not found with mac-address : some-wrong-mac-address");
		exceptionResponse.setStatusCode(HttpStatus.NOT_FOUND);
		exceptionResponse.setUrl("/api/v1/provisioning/some-wrong-mac-address");
		return exceptionResponse;
	}

}
