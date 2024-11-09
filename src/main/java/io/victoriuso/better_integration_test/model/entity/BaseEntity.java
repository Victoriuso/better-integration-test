package io.victoriuso.better_integration_test.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.Instant;

@SuperBuilder(toBuilder = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @UuidGenerator
    protected String id;

    @CreatedDate
    protected Instant createdAt;

    @UpdateTimestamp
    protected Instant updatedAt;

    @CreatedBy
    @Column(updatable = false)
    protected String createdBy;

    protected String updatedBy;
}
