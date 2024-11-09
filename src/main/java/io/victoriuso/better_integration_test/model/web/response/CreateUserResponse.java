package io.victoriuso.better_integration_test.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserResponse {

    private String id;

    private String name;

    private String email;

    private String phoneNumber;
}
