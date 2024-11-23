package io.victoriuso.better_integration_test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class UserControllerIntegrationTest extends BaseIntegrationTestAbstract {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void doLogin_test_success() {
//        final RequestBuilder requestBuilder =
        /*final MvcResult mvcResult = mvc.perform()
                .andReturn();*/
    }


    @Test
    public void doLogin_test_userNotFound_failed() {

    }

    @Test
    public void doLogin_test_userBanned_failed() {

    }
}
