package com.example.payment.ms.dto.request;


import com.example.payment.ms.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = PRIVATE)
public class PaymentRequest {
    Long accountId;

    String name;

    BigDecimal amount;

    PaymentType type;



}
