package com.unifize.discount.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Result class containing price information after applying discounts
 */
@Data
@Builder
public class DiscountedPrice {
    private BigDecimal originalPrice;
    private BigDecimal finalPrice;
    private Map<String, BigDecimal> appliedDiscounts; // discount_name -> amount
    private String message;
}
