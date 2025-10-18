package com.example.payment.ms.mapper;

import com.example.payment.ms.dao.entity.AccountEntity;
import com.example.payment.ms.dao.entity.PaymentEntity;
import com.example.payment.ms.dto.request.PaymentRequest;
import com.example.payment.ms.dto.response.PaymentResponse;
import com.example.payment.ms.kafka.PaymentEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentMapper {

  public PaymentEntity paymentEntity(PaymentRequest request, AccountEntity entity){
      return PaymentEntity.builder()
              .accountId(entity.getId())
              .type(request.getType())
              .amount(request.getAmount())
              .name(request.getName())
              .build();
  }

  public PaymentResponse paymentResponse(PaymentEntity paymentEntity){
      return PaymentResponse.builder()
              .accountId(paymentEntity.getAccountId())
              .id(paymentEntity.getId())
              .amount(paymentEntity.getAmount())
              .createdAt(LocalDateTime.now())
              .type(paymentEntity.getType())
              .status(paymentEntity.getStatus())
              .build();
  }

  public PaymentEvent event(PaymentEntity entity){
      return PaymentEvent.builder()
              .amount(entity.getAmount())
              .type(entity.getType())
              .build();
  }


}
