package com.example.cashcard;

import com.example.config.S3Config;
import com.example.data.CashCardRepository;
import com.example.domain.CashCashRecordWithoutId;
import com.example.entity.CashCard;
import com.example.service.CashCardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
class CashCardServiceTest {

    @Mock
    S3Config s3Config;

    @Mock
    CashCardRepository cashCardRepository;

    @InjectMocks
    CashCardService cashCardService;

    @Test
    public void when_find_all_it_should_return_a_list_of_cash_cards() {
        List<CashCard> mockResponse = new ArrayList<>();
        mockResponse.add(new CashCard(1L, 100.0));
        mockResponse.add(new CashCard(2L, 200.0));
        when(cashCardRepository.findAll()).thenReturn(mockResponse);

        var result = cashCardService.findAll();

        assertThat(mockResponse).isEqualTo(result);

    }
    @Test
    public void when_find_all_it_should_return_an_empty_list_of_cash_cards() {
        List<CashCard> mockResponse = new ArrayList<>();
        when(cashCardRepository.findAll()).thenReturn(mockResponse);

        var result = cashCardService.findAll();

        assertThat(mockResponse).isEqualTo(result);

    }
    @Test
    public void when_save_cash_it_should_return_cash_card_id() {
        Long expectedResult = 20L;
        when(cashCardRepository.save(any())).thenReturn(new CashCard(20L, 30.0));

        var result = cashCardService.saveCashCard(new CashCashRecordWithoutId(100.0));

        assertThat(expectedResult).isEqualTo(result);

    }

    @Test
    public void when_update_cash_card_it_should_return_successful_message() {
        String expectedResult = "Updated successfully!";
        when(cashCardRepository.findById(any())).thenReturn(Optional.of(new CashCard(20L, 30.0)));
        when(cashCardRepository.save(any())).thenReturn(new CashCard(20L, 30.0));

        var result = cashCardService.updateCashCard(20L, new CashCashRecordWithoutId(100.0));

        assertThat(expectedResult).isEqualTo(result);

    }
    @Test
    public void when_update_cash_card_it_should_return_unsuccessful_message() {
        String expectedResult = "Update failed!";
        when(cashCardRepository.findById(any())).thenReturn(Optional.of(new CashCard(0L, 30.0)));
        when(cashCardRepository.save(any())).thenReturn(new CashCard(0L, 30.0));

        var result = cashCardService.updateCashCard(20L, new CashCashRecordWithoutId(100.0));

        assertThat(expectedResult).isEqualTo(result);

    }
}
