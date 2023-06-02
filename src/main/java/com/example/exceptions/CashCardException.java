package com.example.exceptions;

import com.example.domain.ErrorCode;

public class CashCardException extends RuntimeException {
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private final ErrorCode errorCode;
    private final String errorMessage;

    public CashCardException(ErrorCode code, String message){
        super(message);
        this.errorCode = code;
        this.errorMessage = message;
    }




}
