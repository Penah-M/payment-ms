package com.example.payment.ms.service;

import com.example.payment.ms.dto.request.AccountRequest;
import com.example.payment.ms.dto.response.AccountResponse;

public interface AccountService {

     AccountResponse createAccount(AccountRequest request);
     AccountResponse findById(Long id);
     AccountResponse deleteAccount(Long id);


}
