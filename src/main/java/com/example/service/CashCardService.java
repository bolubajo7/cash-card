package com.example.service;

import com.example.data.CashCardRepository;
import com.example.domain.CashCashRecordWithoutId;
import com.example.entity.CashCard;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CashCardService {

    private final CashCardRepository cashCardRepository;

    public CashCardService(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    public List<CashCard> findAll() {

        return (List<CashCard>) cashCardRepository.findAll();
    }

    public CashCard findById(Long id) {
        return cashCardRepository.findById(id).orElseThrow();
    }

    public Long saveCashCard(CashCashRecordWithoutId amount) {
        var cashCardObj = toCashCard(amount);
        var response = cashCardRepository.save(cashCardObj);
        return response.getId();

    }

    private CashCard toCashCard(CashCashRecordWithoutId cashCashRecordWithoutId) {
        return new CashCard(cashCashRecordWithoutId.amount());
    }

    public String updateCashCard(Long Id, CashCashRecordWithoutId cashCashRecordWithoutId) {
        var response = this.findById(Id);
        if (response.getId() > 0) {
            var result = cashCardRepository.save(new CashCard(Id, cashCashRecordWithoutId.amount()));
            if (Objects.equals(result.getId(), Id)) {
                return "Updated successfully!";
            }
        }
        return "Update failed!";
    }
}

// Build data layer to return from an in-memory db or actual db (postgressSQL, mongoDb, h2) use relational dbm DONE
// Set up cash card repository DONE
// Write test cases both positive and negative scenarios
// Think of all the exceptions that the service could return. Error codes in case of an error e.g cc001
// error codes that would be exposed by my service
// How to properly handle exceptions.
// Service unavailable(internal server error) must be avoided by all cause
// When sending a service that is in correct return a bad request
// Sending a valid request and still the server is not able to handle it correctly.

// 400 - BAD Request (priority)
// 503 - Service not available
// Enforce contract that says if your request is bad,
// Minimise Internal server error
// Handle exceptions gracefully
// Anticipate exceptions and handle it
// Try catch block to log exceptions
// API should not expose internals of your system.
// B2B and B2C. Careful if your application is B2C
// Everything should be in a good state before hosting in a remote environment.
// Write test for all sce