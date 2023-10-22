package com.kuriosity.kcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InformationNotFound extends RuntimeException{
    public InformationNotFound(String message){
        super(message);
    }
}
