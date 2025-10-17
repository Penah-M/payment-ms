package com.example.payment.ms.dto.response;


import com.example.payment.ms.enums.AccountStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@FieldDefaults(level = PRIVATE)
public class AccountResponse {

    Long id;

    String name;

    BigDecimal balance;

    AccountStatus status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
