package com.example.payment.ms.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AccountRequest {
    @NotBlank(message = "Ad bos ola bilmez")
    String name;

    @NotNull(message = "Balans menfi(-)ve ya bos olmaz")
    BigDecimal balance;


}
