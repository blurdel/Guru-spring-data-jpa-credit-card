package com.blurdel.sdjpa.creditcard.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blurdel.sdjpa.creditcard.domain.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long>{
}
