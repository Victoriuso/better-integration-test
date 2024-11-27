package io.victoriuso.better_integration_test.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("io.victoriuso.webclient.fraud-check")
@Data
public class FraudCheckClientProperties {

    private String host;
}
