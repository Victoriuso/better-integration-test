package io.victoriuso.better_integration_test.service.impl;

import io.victoriuso.better_integration_test.model.client.response.FraudCheckResponse;
import io.victoriuso.better_integration_test.service.FraudCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FraudCheckServiceImpl implements FraudCheckService {

    private final WebClient webClient;

    /**
     * This is call to api, for example we have fraud service and we want to check if this email is fraud or not
     * @param email
     * @return true or false
     */
    @Override
    public Boolean isEmailFraud(String email) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fraud-check")
                        .queryParam("email", email)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Error")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error connect")))
                .bodyToMono(FraudCheckResponse.class)
                .map(FraudCheckResponse::getFraudDetected)
                .defaultIfEmpty(Boolean.TRUE)
                .block();
    }
}
