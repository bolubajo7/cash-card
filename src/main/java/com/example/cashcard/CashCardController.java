package com.example.cashcard;

import com.example.domain.CashCashRecordWithoutId;
import com.example.entity.CashCard;
import com.example.service.CashCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cashcards")
public class CashCardController {

    private final CashCardService cashCardService;

    public CashCardController(CashCardService cashCardService) {
        this.cashCardService = cashCardService;
    }


    @GetMapping("/{requestId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestId) {
        var response = cashCardService.findById(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll() {
        var response = cashCardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/addCard")
    public Long addCashCard(@RequestBody CashCashRecordWithoutId cashCashRecordWithoutId) {
        return cashCardService.saveCashCard(cashCashRecordWithoutId);
    }

    @PutMapping("/updateCard/{Id}")
    public String updateCashCard(@PathVariable Long Id, @RequestBody CashCashRecordWithoutId cashCashRecordWithoutId) {
        return cashCardService.updateCashCard(Id, cashCashRecordWithoutId);
    }
}
