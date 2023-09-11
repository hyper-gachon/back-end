package com.gachon.hypergachon.global.exception;


import com.gachon.hypergachon.global.response.ErrorMessage;

public class LoginCancelException extends BusinessException{

    public LoginCancelException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
