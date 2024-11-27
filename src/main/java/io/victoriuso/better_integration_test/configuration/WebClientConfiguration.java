package io.victoriuso.better_integration_test.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final FraudCheckClientProperties fraudCheckClientProperties;

    @Bean
    @Qualifier("fraudCheckerClient")
    public WebClient fraudCheckerClient() {
        return WebClient.builder()
                .baseUrl(fraudCheckClientProperties.getHost())
                .build();
    }
}
