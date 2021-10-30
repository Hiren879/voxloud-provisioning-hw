package com.voxloud.provisioning.props;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.voxloud.provisioning.constant.ProvisioningConstant;

@Configuration
@PropertySource("classpath:application.properties")
public class ProvisioningProps {

	@Value("${provisioning.domain}")
	private String domain;

	@Value("${provisioning.port}")
	private String port;

	@Value("${provisioning.codecs}")
	private String codes;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public Map<String, Object> getCommonPropsMap() {
		Map<String, Object> commonPropsMap = new HashMap<>();
		commonPropsMap.put(ProvisioningConstant.DOMAIN, getDomain());
		commonPropsMap.put(ProvisioningConstant.PORT, getPort());
		commonPropsMap.put(ProvisioningConstant.CODECS, getCodes());
		return commonPropsMap;
	}
}
