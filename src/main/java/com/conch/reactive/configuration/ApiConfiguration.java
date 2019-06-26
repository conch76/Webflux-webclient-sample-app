package com.conch.reactive.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.webclient")
@Data
public class ApiConfiguration {
    private String baseUri;
    private int connectTimeoutInMilliSecond;
    private int readTimeoutInSecond;
}
