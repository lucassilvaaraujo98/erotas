package com.erotas.erotas_backend.exception;

public class ApiException extends RuntimeException {
    private final int status;

    public ApiException(String mensagem, int status) {
        super(mensagem);
        this.status = status;
    }
    public int getStatus() { return status; }
}
