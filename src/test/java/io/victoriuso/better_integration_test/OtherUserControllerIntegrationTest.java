package io.victoriuso.better_integration_test;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.victoriuso.better_integration_test.constant.ApiPathConstant;
import io.victoriuso.better_integration_test.model.client.response.FraudCheckResponse;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;


/**
 * This is for example integration test without using folder based for seeding data and mock api
 */
public class OtherUserControllerIntegrationTest extends BaseIntegrationTestAbstract {

    private static final String EMAIL = "alice.smith@example.com";

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {

    }

    @AfterEach
    public void teardown() {
       this.cleanUpData();
    }

    @Test
    public void createUser_test_success() throws Exception {
        this.mockFraudApi(false);

        final String url = ApiPathConstant.USER_API;
        final String username = "alice_smith";
        final CreateUserRequest request = CreateUserRequest.builder()
                .email(EMAIL)
                .fullName("Alice Smith")
                .phoneNumber("1987654321")
                .password("password")
                .username(username)
                .build();
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<CreateUserResponse> response = this.getResponse(mvcResult, CreateUserResponse.class);
        assertEquals(String.valueOf(HttpStatus.OK.value()), response.getCode());

        //assert if the user is inserted
        final User user = userRepository.findByUserId(username).orElse(null);
        assertNotNull(user);

        verify(getRequestedFor(urlPathEqualTo("/fraud-check"))
                .withQueryParam("email", equalTo(EMAIL)));
    }

    @Test
    public void createUser_test_fraudDetected_failed() throws Exception {
        this.mockFraudApi(true);

        final String url = ApiPathConstant.USER_API;
        final String username = "alice_smith";
        final CreateUserRequest request = CreateUserRequest.builder()
                .email(EMAIL)
                .fullName("Alice Smith")
                .phoneNumber("1987654321")
                .password("password")
                .username(username)
                .build();
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<CreateUserResponse> response = this.getResponse(mvcResult, CreateUserResponse.class);
        assertEquals(String.valueOf(HttpStatus.I_AM_A_TEAPOT.value()), response.getCode());

        //assert if the user is failed to insert
        final User user = userRepository.findByUserId(username).orElse(null);
        assertNull(user);

        verify(getRequestedFor(urlPathEqualTo("/fraud-check"))
                .withQueryParam("email", equalTo(EMAIL)));
    }

    @Test
    public void doLogin_test_success() throws Exception {
        this.seedingDataForTest(null);

        final String url = ApiPathConstant.USER_API + ApiPathConstant.DO_LOGIN;
        final LoginRequest loginRequest = LoginRequest.builder()
                .username("johndoe123")
                .password("password")
                .build();
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<LoginResponse> response = this.getResponse(mvcResult, LoginResponse.class);
        assertEquals(String.valueOf(HttpStatus.OK.value()), response.getCode());
        assertNotNull(response.getData().getAccessToken());
    }

    @Test
    public void doLogin_test_userBanned_failed() throws Exception {
        this.seedingDataForTest(Instant.now());
        final String url = ApiPathConstant.USER_API + ApiPathConstant.DO_LOGIN;
        final LoginRequest loginRequest = LoginRequest.builder()
                .username("johndoe123")
                .password("password")
                .build();
        final RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);
        final MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andReturn();
        final BaseResponse<LoginResponse> response = this.getResponse(mvcResult);
        assertEquals(String.valueOf(HttpStatus.I_AM_A_TEAPOT.value()), response.getCode());
    }

    private void cleanUpData() {
        userRepository.deleteAll();
        WireMock.reset();
    }

    //Seeding data
    private void seedingDataForTest(Instant lastBanned) {
        final String encryptedPassword = new BCryptPasswordEncoder().encode("password");
        final User user = User.builder()
                .userId("johndoe123")
                .password(encryptedPassword)
                .lastBanned(lastBanned)
                .build();
        this.userRepository.save(user);
    }

    //Mock API
    private void mockFraudApi(boolean isFraudDetected) throws Exception {
        final FraudCheckResponse response = FraudCheckResponse.builder().email(EMAIL).fraudDetected(isFraudDetected).build();
        stubFor(get(urlPathEqualTo("/fraud-check"))
                .withQueryParam("email", equalTo(EMAIL))
                .willReturn(aResponse()
                        .withBody(objectMapper.writeValueAsString(response))
                        .withHeader("Content-Type", "application/json")
                )
        );
    }
}
