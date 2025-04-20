package br.com.produtos_api.infrastructure.exceptions;

public class ProductNotFound extends RuntimeException {
    public ProductNotFound(String message) {

        super(message);
    }
}
