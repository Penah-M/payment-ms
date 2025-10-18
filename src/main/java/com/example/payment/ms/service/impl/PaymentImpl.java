package com.example.payment.ms.service.impl;

import com.example.payment.ms.dao.entity.AccountEntity;
import com.example.payment.ms.dao.entity.PaymentEntity;
import com.example.payment.ms.dao.repository.AccountRepository;
import com.example.payment.ms.dao.repository.PaymentRepository;
import com.example.payment.ms.dto.request.PaymentRequest;
import com.example.payment.ms.dto.response.PaymentResponse;
import com.example.payment.ms.exception.InsufficientBalanceException;
import com.example.payment.ms.exception.NotFoundException;
import com.example.payment.ms.kafka.PaymentEvent;
import com.example.payment.ms.mapper.PaymentMapper;
import com.example.payment.ms.service.PaymentService;
import com.example.payment.ms.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.payment.ms.enums.PaymentStatus.FAILED;
import static com.example.payment.ms.enums.PaymentStatus.SUCCESS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Slf4j
public class PaymentImpl implements PaymentService {

    PaymentRepository paymentRepository;
    PaymentMapper mapper;

    AccountRepository accountRepository;
    CacheUtil cacheUtil;

    KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    static String TOPIC = "payment";

    @Transactional
    @Override
    public PaymentResponse pay(PaymentRequest request) {
        AccountEntity entity = accountRepository.findById(request.getAccountId()).orElseThrow(() -> {
            log.error("Qeyd etdiyiniz  {} ID hesab movcud deyil ", request.getAccountId());
            return new NotFoundException("Bele bir istifadecili id yoxdur");
        });
        log.info("Account melumati cache elave olundu");
        cacheUtil.saveCache(getKey(entity.getId()), entity, 10L, MINUTES);

        PaymentEntity entity1 = mapper.paymentEntity(request, entity);

        if (entity.getBalance().compareTo(entity1.getAmount()) < 0) {
            log.error("Yetersiz balans:{}, mehsulun qiymeti :{}", entity.getBalance(), entity1.getAmount());
            entity1.setStatus(FAILED);
            paymentRepository.save(entity1);
            cacheUtil.saveCache(getKey(entity1.getId()),entity1,10L, MINUTES);
            throw new InsufficientBalanceException("Balansinizda kifayet qeder vesait yoxdur");
        }

        entity.setBalance(entity.getBalance().subtract(request.getAmount()));
        log.info("Odeme ugurla heyata kecirildi..");
        entity1.setStatus(SUCCESS);
        paymentRepository.save(entity1);
        accountRepository.save(entity);

        PaymentEvent event=mapper.event(entity1);
        log.info("Melumat kafkaya gonderildi");
        kafkaTemplate.send(TOPIC,event);

        return mapper.paymentResponse(entity1);
    }

    @Override
    public List<PaymentResponse> getAllTransaction() {
        return List.of();
    }

    @Override
    public PaymentResponse getBalance(Long id) {
        return null;
    }


    private String getKey(Long id) {
        return "PAYMENT:" + id;
    }
}
