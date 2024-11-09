package io.victoriuso.better_integration_test.controller;

import io.victoriuso.better_integration_test.model.web.request.CreateUserRequest;
import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import io.victoriuso.better_integration_test.model.web.response.CreateUserResponse;
import io.victoriuso.better_integration_test.model.web.response.GetUserResponse;
import io.victoriuso.better_integration_test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/api/better-integration-test/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public BaseResponse<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
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
}
