package com.example.authorbookrest.repository;

import com.example.authorbookrest.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

}