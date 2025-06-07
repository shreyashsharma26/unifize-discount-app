package com.unifize.discount.exception;

/**
 * Exception thrown when discount calculation fails
 */
public class DiscountCalculationException extends RuntimeException {
    public DiscountCalculationException(String message) {
        super(message);
    }
    
    public DiscountCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
