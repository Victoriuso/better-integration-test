package io.victoriuso.better_integration_test.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "io.victoriuso.properties.token")
@Data
public class TokenProperties {

    private String secret;

    private Duration expirationTime;
}
