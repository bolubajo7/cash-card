package com.example.exceptions;

import com.example.domain.ErrorCode;
import com.example.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CashCardException.class)
    public ResponseEntity<ErrorResponse> handleCashCardException(CashCardException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ErrorCode.CC_002.name(), "Cash card not found!"));
    }
}
