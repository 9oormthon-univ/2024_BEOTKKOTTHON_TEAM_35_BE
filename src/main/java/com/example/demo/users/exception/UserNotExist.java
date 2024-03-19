package com.example.demo.users.exception;

import com.example.demo.error.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotExist extends RuntimeException{
    private final ErrorCode errorCode;
    public UserNotExist(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}

