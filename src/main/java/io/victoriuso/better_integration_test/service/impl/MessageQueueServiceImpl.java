package io.victoriuso.better_integration_test.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.victoriuso.better_integration_test.service.MessageQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageQueueServiceImpl implements MessageQueueService {

    private static final String CREATE_USER_TOPIC = "io.victoriuso.publish.user-event";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishCreateUserEvent(String key, Object event) throws JsonProcessingException {
        final String data = objectMapper.writeValueAsString(event);
        kafkaTemplate.send(CREATE_USER_TOPIC, key, data);
    }
}
