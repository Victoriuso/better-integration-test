package io.victoriuso.better_integration_test.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;


@Configuration
@RequiredArgsConstructor
public class CustomAuditorConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(applicationContext.getApplicationName());
    }
}
