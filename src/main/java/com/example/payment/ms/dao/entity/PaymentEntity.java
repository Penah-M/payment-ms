package com.example.payment.ms.dao.entity;

import com.example.payment.ms.enums.PaymentStatus;
import com.example.payment.ms.enums.PaymentType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
@FieldDefaults(level = PRIVATE)
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    Long accountId;

    String name;

    BigDecimal amount;

    @Enumerated(value = STRING)
    PaymentStatus status;

    @Enumerated(value = STRING)

    PaymentType type;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updateAt;
}
