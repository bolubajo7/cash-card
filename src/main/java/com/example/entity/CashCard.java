package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "CASH_CARD")
public class CashCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;

    protected CashCard() {
    }

    public CashCard(Double amount) {
        this.amount = amount;
    }

    public CashCard(Long Id,
                    Double amount) {
        this.id = Id;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, amount='%.2f']",
                id, amount);
    }

    public Double getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }

}
