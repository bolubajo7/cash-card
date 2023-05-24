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
