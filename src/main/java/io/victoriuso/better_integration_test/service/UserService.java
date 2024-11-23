package io.victoriuso.better_integration_test.service;

import io.victoriuso.better_integration_test.model.web.request.CreateUserRequest;
import io.victoriuso.better_integration_test.model.web.request.LoginRequest;
import io.victoriuso.better_integration_test.model.web.response.GetUserResponse;
import io.victoriuso.better_integration_test.model.web.response.LoginResponse;

public interface UserService
{
    void createNewUser(CreateUserRequest createUserRequest);

    GetUserResponse getUser(String id);

    LoginResponse doLogin(LoginRequest loginRequest);

    Boolean doBanUser(String id);
}
