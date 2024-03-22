package com.example.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //user
    USER_NOT_EXIST(500,"US01","USER NOT EXIST"),
    USER_PASSWORD_INCORRECT(500,"US02","USER PASSWORD INCORRECT"),
    EXCHANGE_NOT_EXIST(500,"US03","EXCHANGE NOT EXIST"),
    //scholarship
    SCHOLARSHIP_NOT_EXIST(500,"SC01","SCHOLARSHIP NOT EXIST")
    ;
    final private int status;
    final private String errorCode;
    final private String message;
}
