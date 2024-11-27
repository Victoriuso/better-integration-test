package io.victoriuso.better_integration_test.configuration;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.Metadata;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.matching.ContentPattern;
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder;
import com.github.tomakehurst.wiremock.matching.StringValuePattern;
import com.github.tomakehurst.wiremock.matching.ValueMatcher;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Builder
@RequiredArgsConstructor
public class WireMockHelper {

    private static final String WIREMOCK_PATH = "/integration-test/%s/%s/mock-api";

    private final String className;
    private final String functionName;

    public void registerStubbing() throws IOException {
        final String path = String.format(WIREMOCK_PATH, className, functionName);
        final URL url = getClass().getResource(path);
        if(url != null) {
            final File[] files = new File(url.getPath()).listFiles();
            if (files == null) {
                return;
            }
            for(File file : files) {
                StubMapping mapping = StubMapping.buildFrom(Files.readString(Paths.get(file.getPath()), UTF_8));
                WireMock.stubFor(new CustomMappingBuilder(mapping));
            }
        }

    }

    public static class CustomMappingBuilder implements MappingBuilder {
        private final StubMapping stubMapping;

        public CustomMappingBuilder(StubMapping stubMapping) {
            this.stubMapping = stubMapping;
        }

        @Override
        public MappingBuilder withScheme(String s) {
            return null;
        }

        @Override
        public MappingBuilder withHost(StringValuePattern stringValuePattern) {
            return null;
        }

        @Override
        public MappingBuilder withPort(int i) {
            return null;
        }

        @Override
        public MappingBuilder atPriority(Integer integer) {
            return null;
        }

        @Override
        public MappingBuilder withHeader(String s, StringValuePattern stringValuePattern) {
            return null;
        }

        @Override
        public MappingBuilder withQueryParam(String s, StringValuePattern stringValuePattern) {
            return null;
        }

        @Override
        public MappingBuilder withQueryParams(Map<String, StringValuePattern> map) {
            return null;
        }

        @Override
        public MappingBuilder withRequestBody(ContentPattern<?> contentPattern) {
            return null;
        }

        @Override
        public MappingBuilder withMultipartRequestBody(MultipartValuePatternBuilder multipartValuePatternBuilder) {
            return null;
        }

        @Override
        public ScenarioMappingBuilder inScenario(String s) {
            return null;
        }

        @Override
        public MappingBuilder withId(UUID uuid) {
            return null;
        }

        @Override
        public MappingBuilder withName(String s) {
            return null;
        }

        @Override
        public MappingBuilder persistent() {
            return null;
        }

        @Override
        public MappingBuilder persistent(boolean b) {
            return null;
        }

        @Override
        public MappingBuilder withBasicAuth(String s, String s1) {
            return null;
        }

        @Override
        public MappingBuilder withCookie(String s, StringValuePattern stringValuePattern) {
            return null;
        }

        @Override
        public <P> MappingBuilder withPostServeAction(String s, P p) {
            return null;
        }

        @Override
        public MappingBuilder withMetadata(Map<String, ?> map) {
            return null;
        }

        @Override
        public MappingBuilder withMetadata(Metadata metadata) {
            return null;
        }

        @Override
        public MappingBuilder withMetadata(Metadata.Builder builder) {
            return null;
        }

        @Override
        public MappingBuilder andMatching(ValueMatcher<Request> valueMatcher) {
            return null;
        }

        @Override
        public MappingBuilder andMatching(String s) {
            return null;
        }

        @Override
        public MappingBuilder andMatching(String s, Parameters parameters) {
            return null;
        }

        @Override
        public MappingBuilder willReturn(ResponseDefinitionBuilder responseDefinitionBuilder) {
            return null;
        }

        @Override
        public StubMapping build() {
            return this.stubMapping;
        }
    }
}
