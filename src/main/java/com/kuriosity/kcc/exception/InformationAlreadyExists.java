package com.kuriosity.kcc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InformationAlreadyExists extends RuntimeException{
    public InformationAlreadyExists(String message){
        super(message);
    }
}
