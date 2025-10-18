package com.example.payment.ms.mapper;

import com.example.payment.ms.dao.entity.AccountEntity;
import com.example.payment.ms.dto.request.AccountRequest;
import com.example.payment.ms.dto.response.AccountResponse;
import com.example.payment.ms.enums.AccountStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountMapper {
    public AccountEntity entity(AccountRequest request){
     return    AccountEntity.builder()
                .balance(request.getBalance())
                .name(request.getName())
                .status(AccountStatus.ACTIVE)
                .build();
    }

    public AccountResponse response(AccountEntity entity){
        return AccountResponse.builder()
                .balance(entity.getBalance())
                .id(entity.getId())
                .createdAt(LocalDateTime.now())
                .name(entity.getName())
                .status(entity.getStatus())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
