package com.example.payment.ms.service.impl;

import com.example.payment.ms.dao.entity.AccountEntity;
import com.example.payment.ms.dao.repository.AccountRepository;
import com.example.payment.ms.dto.request.AccountRequest;
import com.example.payment.ms.dto.response.AccountResponse;
import com.example.payment.ms.enums.AccountStatus;
import com.example.payment.ms.exception.NotFoundException;
import com.example.payment.ms.mapper.AccountMapper;
import com.example.payment.ms.service.AccountService;
import com.example.payment.ms.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

import static lombok.AccessLevel.PRIVATE;

@Service
@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountImpl implements AccountService {

    AccountMapper accountMapper;
    AccountRepository accountRepository;
    CacheUtil cacheUtil;

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        log.info("Account yaradilmaga basladi");
        AccountEntity entity = accountMapper.entity(request);
        log.info("Bazaya melumat elave edildi");
        accountRepository.save(entity);
        log.info("Redise elave olundu");
        cacheUtil.saveCache(getKey(entity.getId()), entity, 10L, ChronoUnit.MINUTES);
        AccountResponse response = accountMapper.response(entity);
        log.info("Account yaradilmagi sona catdi");
        return response;
    }

    @Override
    public AccountResponse findById(Long id) {

        AccountEntity cacheEntity = cacheUtil.getBucket(getKey(id));
        if (cacheEntity != null) {
            log.info("{}-e sahib melumat redisden oxunur.", id);
            return accountMapper.response(cacheEntity);
        }
        AccountEntity account = accountRepository.findById(id).orElseThrow(() -> {
            log.error("BU idli istifadeci tapilmadi{}", id);
            return new NotFoundException("Bu ide sahib istifadeci tapilmadi");
        });
        log.info("Melumat cache elave olundu");
        cacheUtil.saveCache(getKey(account.getId()), account, 10L, ChronoUnit.MINUTES);
        AccountResponse response = accountMapper.response(account);
        log.info("Melumat teqdim olunur");
        return response;
    }

    @Override
    public AccountResponse deleteAccount(Long id) {
        AccountEntity entity = cacheUtil.getBucket(getKey(id));
        if (entity != null) {
            entity.setStatus(AccountStatus.DELETE);
            accountRepository.save(entity);
            return accountMapper.response(entity);
        }
        AccountEntity account = accountRepository.findById(id).orElseThrow(() -> {
            log.error("Bu ID li istifadeci tapilmadi{}", id);
            return new NotFoundException("Melumat tapilmadi");
        });
        account.setStatus(AccountStatus.DELETE);
        accountRepository.save(account);
        log.info("Cache elave olundu..");
        cacheUtil.saveCache(getKey(id), account, 10L, ChronoUnit.MINUTES);
        AccountResponse response = accountMapper.response(account);

        return response;
    }

    @Override
    public void blockAccount(Long id) {
        AccountEntity account = cacheUtil.getBucket(getKey(id));
        if (account != null && account.getStatus().equals(AccountStatus.ACTIVE)) {
            log.info("Redisden oxunur");
            account.setStatus(AccountStatus.BLOCK);
            accountRepository.save(account);
        }


    AccountEntity entity=accountRepository.findById(id).orElseThrow(() -> {
            log.error("Bele id yoxdur");
            return new NotFoundException("Bele id yoxdur");
        });
        entity.setStatus(AccountStatus.BLOCK);
        accountRepository.save(entity);
    }


    private String getKey(Long id) {
        return "RESTAURANT:" + id;
    }
}
