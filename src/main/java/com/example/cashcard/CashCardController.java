package com.example.cashcard;

import com.example.domain.CashCashRecordWithoutId;
import com.example.entity.CashCard;
import com.example.service.CashCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/v1/cashcards")
public class CashCardController {

    private final CashCardService cashCardService;

    public CashCardController(CashCardService cashCardService) {
        this.cashCardService = cashCardService;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<CashCard> findById(@PathVariable Long requestId) {
        try {
            var response = cashCardService.findById(requestId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CashCard>> findAll() {
        var response = cashCardService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/addCard")
    public ResponseEntity<Long> addCashCard(@RequestBody CashCashRecordWithoutId cashCashRecordWithoutId) {
        var response = cashCardService.saveCashCard(cashCashRecordWithoutId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/updateCard/{Id}")
    public ResponseEntity<String> updateCashCard(@PathVariable Long Id, @RequestBody CashCashRecordWithoutId cashCashRecordWithoutId) {
        try {
            var response = cashCardService.updateCashCard(Id, cashCashRecordWithoutId);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (NoSuchElementException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
