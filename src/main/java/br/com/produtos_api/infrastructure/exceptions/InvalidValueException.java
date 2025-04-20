package br.com.produtos_api.infrastructure.exceptions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {

        super(message);
    }

}


