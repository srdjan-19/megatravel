package com.megatravel.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CustomProperties.class})
@ConfigurationProperties(prefix = "properties", ignoreUnknownFields = false)
public class CustomProperties {

    private String jwtSecret;

    private int jwtExpirationInMs;

    private Long maxAgeSecs;

    private String frontBaseUrl;

	public String getJwtSecret() {
		return jwtSecret;
	}

	public void setJwtSecret(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	public int getJwtExpirationInMs() {
		return jwtExpirationInMs;
	}

	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}

	public Long getMaxAgeSecs() {
		return maxAgeSecs;
	}

	public void setMaxAgeSecs(Long maxAgeSecs) {
		this.maxAgeSecs = maxAgeSecs;
	}

	public String getFrontBaseUrl() {
		return frontBaseUrl;
	}

	public void setFrontBaseUrl(String frontBaseUrl) {
		this.frontBaseUrl = frontBaseUrl;
	}
}