package com.example.payment.ms.dto.response;

import com.example.payment.ms.enums.PaymentStatus;
import com.example.payment.ms.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.weaver.tools.cache.CachedClassReference;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class PaymentResponse {
    Long id;

    Long accountId;

    String name;

    BigDecimal amount;

    PaymentType type;

    PaymentStatus status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
