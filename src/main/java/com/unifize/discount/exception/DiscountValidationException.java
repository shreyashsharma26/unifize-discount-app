package com.unifize.discount.exception;

/**
 * Exception thrown when discount validation fails
 */
public class DiscountValidationException extends RuntimeException {
    public DiscountValidationException(String message) {
        super(message);
    }
    
    public DiscountValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
