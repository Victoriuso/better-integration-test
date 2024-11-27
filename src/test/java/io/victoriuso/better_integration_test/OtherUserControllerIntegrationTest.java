package io.victoriuso.better_integration_test;

import io.victoriuso.better_integration_test.constant.ApiPathConstant;
import io.victoriuso.better_integration_test.model.entity.User;
import io.victoriuso.better_integration_test.model.web.request.LoginRequest;
import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import io.victoriuso.better_integration_test.model.web.response.LoginResponse;
import io.victoriuso.better_integration_test.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * This is for example integration test
 */
public class OtherUserControllerIntegrationTest extends BaseIntegrationTestAbstract {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    @Disabled
    public void doLogin_test_success() throws Exception {
        this.seedingDataForTest(null);
        final String url = ApiPathConstant.USER_API + ApiPathConstant.DO_LOGIN;
        final LoginRequest loginRequest = LoginRequest.builder()
                .username("johndoe123")
                .password("password")
                .build();
        final RequestBuilder requestBuilder = post(url)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<LoginResponse> response = this.getResponse(mvcResult, LoginResponse.class);
        assertEquals(String.valueOf(HttpStatus.OK.value()), response.getCode());
        assertNotNull(response.getData().getAccessToken());
        this.cleanUpData();
    }

    @Test
    public void doLogin_test_userBanned_failed() throws Exception {
        this.seedingDataForTest(Instant.now());
        final String url = ApiPathConstant.USER_API + ApiPathConstant.DO_LOGIN;
        final LoginRequest loginRequest = LoginRequest.builder()
                .username("johndoe123")
                .password("password")
                .build();
        final RequestBuilder requestBuilder = post(url)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<LoginResponse> response = this.getResponse(mvcResult);
        assertEquals(String.valueOf(HttpStatus.I_AM_A_TEAPOT.value()), response.getCode());
        this.cleanUpData();
    }

    private void cleanUpData() {
        userRepository.deleteAll();
    }

    //Seeding data
    private void seedingDataForTest(Instant lastBanned) {
        final User user = User.builder()
                .userId("johndoe123")
                .password("password")
                .lastBanned(lastBanned)
                .build();
        this.userRepository.save(user);
    }
}
