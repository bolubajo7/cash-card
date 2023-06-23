package com.example.cashcard;

import com.example.config.S3Config;
import com.example.domain.CashCashRecordWithoutId;
import com.example.entity.CashCard;
import com.example.service.CashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/cashcards",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CashCardController {

    private final CashCardService cashCardService;

    public CashCardController(CashCardService cashCardService) {
        this.cashCardService = cashCardService;
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@PathVariable Long requestId) {
            var response = cashCardService.findById(requestId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
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
            var response = cashCardService.updateCashCard(Id, cashCashRecordWithoutId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
