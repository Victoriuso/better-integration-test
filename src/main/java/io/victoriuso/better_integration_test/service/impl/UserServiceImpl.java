package io.victoriuso.better_integration_test.service.impl;

import io.victoriuso.better_integration_test.model.entity.MyUser;
import io.victoriuso.better_integration_test.model.web.request.CreateUserRequest;
import io.victoriuso.better_integration_test.repository.MyUserRepository;
import io.victoriuso.better_integration_test.service.MessageQueueService;
import io.victoriuso.better_integration_test.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MyUserRepository myUserRepository;
    private final MessageQueueService messageQueueService;

    @Override
    public void createNewUser(CreateUserRequest createUserRequest) {

        final String password = new BCryptPasswordEncoder().encode(createUserRequest.getPassword());
        final MyUser myUser = MyUser
                .builder()
                .userId(createUserRequest.getUsername())
                .password(password)
                .email(createUserRequest.getEmail())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .fullName(createUserRequest.getName())
                .build();
        myUserRepository.save(myUser);
    }

    @Override
    public MyUser getUser(String id) {
        final MyUser myUser = myUserRepository.findById(id).orElse(null);
        if(myUser == null) {
            throw new EntityNotFoundException(id);
        }

        if (myUser.getLastBanned() != null) {
            throw new RuntimeException("User is already banned");
        }
        return myUser;
    }
}
