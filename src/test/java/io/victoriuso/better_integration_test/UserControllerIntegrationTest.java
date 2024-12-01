package io.victoriuso.better_integration_test;

import io.victoriuso.better_integration_test.constant.ApiPathConstant;
import io.victoriuso.better_integration_test.model.entity.User;
import io.victoriuso.better_integration_test.model.web.request.CreateUserRequest;
import io.victoriuso.better_integration_test.model.web.request.LoginRequest;
import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import io.victoriuso.better_integration_test.model.web.response.CreateUserResponse;
import io.victoriuso.better_integration_test.model.web.response.LoginResponse;
import io.victoriuso.better_integration_test.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class UserControllerIntegrationTest extends BaseIntegrationTestAbstract {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
        userRepository.deleteAll();
    }

    @Test
    @Sql(scripts = {
            "/integration-test/UserControllerIntegrationTest/createUser_test_success/db/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createUser_test_success() throws Exception {
        final String url = ApiPathConstant.USER_API;
        final String username = "alice_smith";
        final String email = "alice.smith@example.com";
        final CreateUserRequest request = CreateUserRequest.builder()
                .email(email)
                .fullName("Alice Smith")
                .phoneNumber("1987654321")
                .password("password")
                .username(username)
                .build();
        final RequestBuilder requestBuilder = post(url)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<CreateUserResponse> response = this.getResponse(mvcResult, CreateUserResponse.class);
        assertEquals(String.valueOf(HttpStatus.OK.value()), response.getCode());

        //assert if the user is inserted
        final User user = userRepository.findByUserId(username).orElse(null);
        assertNotNull(user);

        //assert API
        verify(getRequestedFor(urlPathEqualTo("/fraud-check"))
                .withQueryParam("email", equalTo(email)));
    }

    @Test
    @Sql(scripts = {
            "/integration-test/UserControllerIntegrationTest/doLogin_test_success/db/insert_user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "/integration-test/UserControllerIntegrationTest/doLogin_test_success/db/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doLogin_test_success() throws Exception {
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
    }

    @Test
    public void doLogin_test_userNotFound_failed() throws Exception {
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
    }

    @Test
    @Sql(scripts = {
            "/integration-test/UserControllerIntegrationTest/doLogin_test_userBanned_failed/db/insert_user.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "/integration-test/UserControllerIntegrationTest/doLogin_test_userBanned_failed/db/clean.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doLogin_test_userBanned_failed() throws Exception {
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
    }
}
