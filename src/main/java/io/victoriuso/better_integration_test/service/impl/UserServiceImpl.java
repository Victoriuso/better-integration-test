package io.victoriuso.better_integration_test.service.impl;

import io.victoriuso.better_integration_test.model.entity.User;
import io.victoriuso.better_integration_test.model.web.request.CreateUserRequest;
import io.victoriuso.better_integration_test.model.web.request.LoginRequest;
import io.victoriuso.better_integration_test.model.web.response.GetUserResponse;
import io.victoriuso.better_integration_test.model.web.response.LoginResponse;
import io.victoriuso.better_integration_test.repository.UserRepository;
import io.victoriuso.better_integration_test.service.MessageQueueService;
import io.victoriuso.better_integration_test.service.TokenHelperService;
import io.victoriuso.better_integration_test.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MessageQueueService messageQueueService;
    private final TokenHelperService tokenHelperService;

    @Override
    public void createNewUser(CreateUserRequest createUserRequest) {
        final String password = new BCryptPasswordEncoder().encode(createUserRequest.getPassword());
        final User user = User
                .builder()
                .userId(createUserRequest.getUsername())
                .password(password)
                .email(createUserRequest.getEmail())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .fullName(createUserRequest.getFullName())
                .build();
        userRepository.save(user);
    }

    @Override
    public GetUserResponse getUser(String id) {
        final User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new EntityNotFoundException(id);
        }

        if (user.getLastBanned() != null) {
            throw new RuntimeException("User is already banned");
        }

        final GetUserResponse getUserResponse = GetUserResponse.builder()
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build();
        return getUserResponse;
    }

    @Override
    public LoginResponse doLogin(LoginRequest loginRequest) {
        final User user = userRepository.findByUserId(loginRequest.getUsername()).orElse(null);
        if(user == null) {
            throw new EntityNotFoundException(loginRequest.getUsername());
        }

        final boolean isPasswordMatch = new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword());
        if(!isPasswordMatch) {
            throw new EntityNotFoundException(loginRequest.getUsername());
        }

        if (user.getLastBanned() != null) {
            throw new RuntimeException("User is already banned");
        }

        final String token = tokenHelperService.generateToken(user);
        return LoginResponse.builder()
                .accessToken(token)
                .build();
    }

    @Override
    public Boolean toggleBannedUser(String id, boolean isUserBanned) {
        final User user = userRepository.findByUserId(id).orElse(null);
        if(user == null) {
            throw new EntityNotFoundException(id);
        }

        if (user.getLastBanned() != null) {
            return false;
        }

        final Instant lastBanned = isUserBanned ? Instant.now() : null;
        user.setLastBanned(lastBanned);
        userRepository.save(user);
        return true;
    }
}
