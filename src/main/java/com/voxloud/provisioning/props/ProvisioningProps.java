package com.voxloud.provisioning.props;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.voxloud.provisioning.constant.ProvisioningConstant;

import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
@Setter
public class ProvisioningProps {

	@Value("${provisioning.domain}")
	private String domain;

	@Value("${provisioning.port}")
	private String port;

	@Value("${provisioning.codecs}")
	private String codes;

	public Map<String, Object> getCommonPropsMap() {
		Map<String, Object> commonPropsMap = new HashMap<>();
		commonPropsMap.put(ProvisioningConstant.DOMAIN, getDomain());
		commonPropsMap.put(ProvisioningConstant.PORT, getPort());
		commonPropsMap.put(ProvisioningConstant.CODECS, getCodes());
		return commonPropsMap;
	}
}
