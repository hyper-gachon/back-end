package com.gachon.hypergachon.exception;


import com.gachon.hypergachon.response.ErrorMessage;

public class LoginCancelException extends BusinessException{

    public LoginCancelException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
