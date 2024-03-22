package com.example.demo.scholarship.exception;

import com.example.demo.error.ErrorCode;
import lombok.Getter;

@Getter
public class ScholarshipNotExist extends RuntimeException{
    private final ErrorCode errorCode;
    public ScholarshipNotExist(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
