package io.victoriuso.better_integration_test.service;

import io.victoriuso.better_integration_test.model.entity.User;

public interface TokenHelperService {
    String generateToken(User user);
}
