package io.victoriuso.better_integration_test.model.entity;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity(name = "my_user")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MyUser extends BaseEntity {

    private String userId;

    private String password;

    private String phoneNumber;

    private String email;

    private String fullName;

    private Instant lastLogin;

    private Instant lastBanned;
}
