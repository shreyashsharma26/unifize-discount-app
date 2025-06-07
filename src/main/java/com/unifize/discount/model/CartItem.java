package com.unifize.discount.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an item in the shopping cart
 */
@Data
@Builder
public class CartItem {
    private Product product;
    private int quantity;
    private String size;
}
