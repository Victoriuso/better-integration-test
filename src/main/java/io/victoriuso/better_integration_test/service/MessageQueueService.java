package io.victoriuso.better_integration_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessageQueueService {
    void publishCreateUserEvent(String key, Object event) throws JsonProcessingException;
}
