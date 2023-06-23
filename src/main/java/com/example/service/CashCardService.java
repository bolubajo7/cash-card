package com.example.service;

import com.example.config.S3Config;
import com.example.data.CashCardRepository;
import com.example.domain.CashCashRecordWithoutId;
import com.example.entity.CashCard;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.List;
import java.util.Objects;

@Service
public class CashCardService {

    private final CashCardRepository cashCardRepository;

    private final S3Config s3Config;

    public CashCardService(CashCardRepository cashCardRepository, S3Config s3Config) {
        this.cashCardRepository = cashCardRepository;
        this.s3Config = s3Config;
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

    private void printBuckets() {
        ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();
        ListBucketsResponse listBucketsResponse = s3Config.getS3Client().listBuckets(listBucketsRequest);
        listBucketsResponse.buckets().forEach(x -> System.out.println(x.name()));

    }


    // create a connection to sqs queue
    // create a producer, the job is to receive a message and publish message to queue
    // service should be able to receive a request and put the message to the queue
    // next step is to connect to sqs queue
}
