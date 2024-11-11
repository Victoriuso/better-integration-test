package io.victoriuso.better_integration_test.model.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseResponse<T> {

    private String code;
    private String status;
    private T data;
    private Object errors;
}
