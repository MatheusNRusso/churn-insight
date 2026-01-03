package com.churninsight.api.error;

import org.springframework.http.HttpStatus;

public class EmailAlreadyRegisteredException extends BusinessException {

    public EmailAlreadyRegisteredException(String email) {
        super("Email already registered: " + email, HttpStatus.CONFLICT);
    }
}
