package com.example.cashcard;

import com.example.entity.CashCard;
import com.example.service.CashCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.equalTo;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CashCardController.class)
public class CashCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CashCardService service;

    @Test
    public void findByIdShouldReturnObjectFromService() throws Exception {
        var expected = objectMapper.writeValueAsString(new CashCard(20L, 100.0));
        when(service.findById(anyLong())).thenReturn(new CashCard(20L, 100.0));
        this.mockMvc.perform(get("/v1/cashcards/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .json(expected));
    }

    @Test
    public void findByIdShouldReturnNotFound() throws Exception {
        when(service.findById(anyLong())).thenThrow(new NoSuchElementException());
        this.mockMvc.perform(get("/v1/cashcards/1").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotFound());
    }


    @Test
    public void findAllShouldReturnListOfObjectsFromService() throws Exception {
        List<CashCard> expected = listOf(new CashCard(1L, 100.0), new CashCard(2L, 200.0));
        var expectedResult = objectMapper.writeValueAsString(expected);
        List<CashCard> mockResponse = new ArrayList<>();
        mockResponse.add(new CashCard(1L, 100.0));
        mockResponse.add(new CashCard(2L, 200.0));
        when(service.findAll()).thenReturn(mockResponse);
        this.mockMvc.perform(get("/v1/cashcards").contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .json(expectedResult));
    }

    @Test
    public void addCardShouldReturnIdFromService() throws Exception {
        var requestJson = objectMapper.writeValueAsString(new CashCard(150.0));
        Long expected = 10L;
        when(service.saveCashCard(any())).thenReturn(10L);
        this.mockMvc.perform(post("/v1/cashcards/addCard").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(String.valueOf(expected)));
    }

    @Test
    public void addCardShouldReturnBadRequest() throws Exception {
        when(service.saveCashCard(any())).thenReturn(10L);
        this.mockMvc.perform(post("/v1/cashcards/addCard").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCashCardShouldReturnSuccessfulMessageFromService() throws Exception {
        var requestJson = objectMapper.writeValueAsString(new CashCard(150.0));
        when(service.updateCashCard(any(), any())).thenReturn("Updated successfully!");
        this.mockMvc.perform(put("/v1/cashcards/updateCard/1").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully!")));
    }

    @Test
    public void updateCashCardShouldReturnUnsuccessfulMessageFromService() throws Exception {
        var requestJson = objectMapper.writeValueAsString(new CashCard(150.0));
        when(service.updateCashCard(any(), any())).thenReturn("Update failed!");
        this.mockMvc.perform(put("/v1/cashcards/updateCard/1").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Update failed!")));
    }

    @Test
    public void updateCashCardShouldReturnNotFound() throws Exception {
        var requestJson = objectMapper.writeValueAsString(new CashCard(150.0));
        when(service.updateCashCard(any(), any())).thenThrow(new NoSuchElementException());
        this.mockMvc.perform(put("/v1/cashcards/updateCard/1").contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateCashCardShouldReturnBadRequest() throws Exception {
        when(service.updateCashCard(any(), any())).thenReturn("");
        this.mockMvc.perform(put("/v1/cashcards/updateCard/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
