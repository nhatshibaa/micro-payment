package com.example.demo_payment_choreogrphy.repository;


import com.example.demo_payment_choreogrphy.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findByUserName(String name);
}
