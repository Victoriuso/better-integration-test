package io.victoriuso.better_integration_test.repository;

import io.victoriuso.better_integration_test.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserId(String username);
}
