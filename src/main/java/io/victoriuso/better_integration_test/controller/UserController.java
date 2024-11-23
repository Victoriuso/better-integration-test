package io.victoriuso.better_integration_test.controller;

import io.victoriuso.better_integration_test.constant.ApiPathConstant;
import io.victoriuso.better_integration_test.model.web.request.CreateUserRequest;
import io.victoriuso.better_integration_test.model.web.request.LoginRequest;
import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import io.victoriuso.better_integration_test.model.web.response.CreateUserResponse;
import io.victoriuso.better_integration_test.model.web.response.GetUserResponse;
import io.victoriuso.better_integration_test.model.web.response.LoginResponse;
import io.victoriuso.better_integration_test.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = ApiPathConstant.USER_API)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public BaseResponse<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        log.info("API create user is hit");
        this.userService.createNewUser(request);
        return BaseResponse.<CreateUserResponse>builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .status(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }

    @GetMapping(path = "/{id}")
    public BaseResponse<GetUserResponse> getUser(@PathVariable String id) {
        final GetUserResponse myUser = this.userService.getUser(id);
        return BaseResponse.<GetUserResponse>builder()
            .code(String.valueOf(HttpStatus.OK.value()))
            .status(HttpStatus.OK.getReasonPhrase())
            .data(myUser)
            .build();
    }


    @PostMapping(path = "/login")
    public BaseResponse<LoginResponse> doLogin(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        log.info("API login request is hit");
        final LoginResponse loginResponse = this.userService.doLogin(loginRequest);
        return BaseResponse.<LoginResponse>builder()
            .code(String.valueOf(HttpStatus.OK.value()))
            .status(HttpStatus.OK.getReasonPhrase())
            .data(loginResponse)
            .build();
    }

    @PatchMapping(path = "/{id}")
    public BaseResponse<Boolean> toggleBannedUser(
            @PathVariable String id,
            @RequestParam String isBanned
    ) {
        log.info("API ban user is hit");
        final Boolean result = this.userService.toggleBannedUser(id, Boolean.parseBoolean(isBanned));
        return BaseResponse.<Boolean>builder()
                .code(String.valueOf(HttpStatus.OK.value()))
                .status(HttpStatus.OK.getReasonPhrase())
                .data(result)
                .build();
    }
}
