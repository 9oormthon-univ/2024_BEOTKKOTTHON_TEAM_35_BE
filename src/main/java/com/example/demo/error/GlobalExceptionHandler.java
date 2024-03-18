package com.example.demo.error;

import com.example.demo.users.exception.UserNotExist;
import com.example.demo.users.exception.UserPasswordIncorrect;
import com.example.demo.users.exception.ExchangeNotExist;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotExist.class)
    public ResponseEntity<ErrorResponse> handleAccountsNotExist(UserNotExist ex){
        log.error("handleUserNotExist",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
    @ExceptionHandler(UserPasswordIncorrect.class)
    public ResponseEntity<ErrorResponse> handleAccountsPasswordIncorrect(UserPasswordIncorrect ex){
        log.error("handleUserPasswordIncorrect",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(ExchangeNotExist.class)
    public ResponseEntity<ErrorResponse> handleImageFileNot(ExchangeNotExist ex){
        log.error("handleExchangeNotExist",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
}

