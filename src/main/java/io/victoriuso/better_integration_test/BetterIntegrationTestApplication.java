package io.victoriuso.better_integration_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BetterIntegrationTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetterIntegrationTestApplication.class, args);
	}

}
