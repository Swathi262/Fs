package com.cg.apigateway.exception;

public class AuthorizationHeaderMissingException extends Exception {
    public AuthorizationHeaderMissingException(String jwtTokenMissingInHeader) {
        super(jwtTokenMissingInHeader);
    }
}
