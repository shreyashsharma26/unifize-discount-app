package com.unifize.discount.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents a product in the e-commerce system
 */
@Data
@Builder
public class Product {
    private String id;
    private String brand;
    private BrandTier brandTier;
    private String category;
    private BigDecimal basePrice;
    private BigDecimal currentPrice; // After brand/category discount
}
