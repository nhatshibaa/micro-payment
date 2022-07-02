package com.example.demo_payment_choreogrphy.service;

import com.example.demo_payment_choreogrphy.entity.Payment;
import com.example.demo_payment_choreogrphy.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    public Optional<Payment> findByName(String name){
        return paymentRepository.findByUserName(name);
    }

    public boolean deductPaymentCheck(String name ,Integer money){
        Payment payment = paymentRepository.findByUserName(name).orElse(null);
        log.info("payment tim deductPaymentCheck la" + payment);
        if (payment == null){
            log.info("payment khong tim thay deductPaymentCheck");
            return false;
        }

        if (payment.getWallet() <= 0){
            log.info("payment getWallet <= 0 deductPaymentCheck");
            return false;
        }

        Integer walletCheck = payment.getWallet() - money;
        if (walletCheck <= 0){
            log.info("payment.getWallet() - money <= 0 deductPaymentCheck");
            return false;
        }

        return deductPayment(name,money);
    }

    public boolean deductPayment (String name ,Integer money){
        try{
            Payment payment = paymentRepository.findByUserName(name).orElse(null);
            log.info("payment tim deductPaymentCheck la" + payment);
            payment.setWallet(payment.getWallet() - money);
            paymentRepository.save(payment);
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
