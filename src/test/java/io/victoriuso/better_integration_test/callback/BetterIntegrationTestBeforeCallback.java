package io.victoriuso.better_integration_test.callback;

import io.victoriuso.better_integration_test.configuration.WireMockHelper;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BetterIntegrationTestBeforeCallback implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        final String className = extensionContext.getRequiredTestClass().getSimpleName();
        final String methodName = extensionContext.getRequiredTestMethod().getName();
        final WireMockHelper wireMockHelper = WireMockHelper.builder()
                .className(className)
                .functionName(methodName)
                .build();
        wireMockHelper.registerStubbing();
    }
}
