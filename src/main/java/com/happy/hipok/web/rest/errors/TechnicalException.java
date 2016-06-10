package com.happy.hipok.web.rest.errors;

/**
 * Technical exception
 */
public class TechnicalException extends RuntimeException {

    public TechnicalException() {
    }

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public TechnicalException(Throwable cause) {
        super(cause);
    }

    public ParameterizedErrorDTO getErrorDTO() {
        return new ParameterizedErrorDTO(this.getMessage());
    }
}