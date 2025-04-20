package br.com.produtos_api.infrastructure.exceptions;

public class ServerException extends RuntimeException {
    public ServerException(String message) {

        super(message);
    }
}
