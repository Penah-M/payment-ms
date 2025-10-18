package com.example.payment.ms.service;

import com.example.payment.ms.dto.request.PaymentRequest;
import com.example.payment.ms.dto.response.PaymentResponse;

import java.util.List;

public interface PaymentService {

    PaymentResponse pay (PaymentRequest request);
    List<PaymentResponse> getAllTransaction();
    PaymentResponse getBalance(Long id);
}
