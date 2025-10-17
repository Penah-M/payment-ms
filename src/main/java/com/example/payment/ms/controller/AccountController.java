package com.example.payment.ms.controller;

import com.example.payment.ms.dto.request.AccountRequest;
import com.example.payment.ms.dto.response.AccountResponse;
import com.example.payment.ms.service.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("account")
@FieldDefaults(level = PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class AccountController {
    AccountService accountService;

    @PostMapping("create")
    public AccountResponse createAccount(@RequestBody @Valid AccountRequest request){
        return accountService.createAccount(request);
    }

    @GetMapping("{id}/find")
    public AccountResponse findById(@PathVariable Long id){
        return accountService.findById(id);
    }

    @PutMapping("{id}/delete")
    public AccountResponse deleteAccount(@PathVariable Long id){
        return accountService.deleteAccount(id);
    }
}
