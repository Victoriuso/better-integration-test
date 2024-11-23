package io.victoriuso.better_integration_test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.victoriuso.better_integration_test.model.web.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { BetterIntegrationTestApplication.class })
@EnableAutoConfiguration
@AutoConfigureMockMvc
@EnableConfigurationProperties
class BaseIntegrationTestAbstract {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    protected <T> BaseResponse<T> getResponse(MvcResult mvcResult) throws Exception {
        final TypeReference<BaseResponse<T>> typeReference = new TypeReference<>() {};
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), typeReference);
    }


    protected <T> BaseResponse<T> getResponse(MvcResult mvcResult, Class<T> clazz) throws Exception {
        final JavaType listType =
                objectMapper.getTypeFactory().constructParametricType(BaseResponse.class, clazz);
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), listType);
    }
}
