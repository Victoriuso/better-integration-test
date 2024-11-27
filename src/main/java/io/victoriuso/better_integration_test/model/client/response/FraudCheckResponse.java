package io.victoriuso.better_integration_test.model.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FraudCheckResponse {

    private String email;
    private Boolean fraudDetected;
}
