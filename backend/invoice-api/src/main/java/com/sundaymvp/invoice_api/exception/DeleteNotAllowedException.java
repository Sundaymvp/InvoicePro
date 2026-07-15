package com.sundaymvp.invoice_api.exception;

public class DeleteNotAllowedException extends RuntimeException {

    public DeleteNotAllowedException(String message) {
        super(message);
    }
}