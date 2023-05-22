package com.example.data;

import com.example.entity.CashCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashCardRepository extends JpaRepository<CashCard, Long> {
}
