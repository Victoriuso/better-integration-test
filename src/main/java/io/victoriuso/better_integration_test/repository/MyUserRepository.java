package io.victoriuso.better_integration_test.repository;

import io.victoriuso.better_integration_test.model.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, String> {

}
