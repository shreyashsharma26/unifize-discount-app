package com.unifize.discount.service.strategy;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DiscountStrategy {

    /**
     * Apply this discount and return discount amount per call.
     * @param currentPrice Current total price before current discount
     * @param cartItems List of items in cart
     * @param customer Customer profile
     * @param paymentInfo Optional payment info
     * @param appliedDiscounts Discounts map to add into
     * @return Discount amount to subtract
     */
    BigDecimal applyDiscount(BigDecimal currentPrice,
                             List<CartItem> cartItems,
                             CustomerProfile customer,
                             PaymentInfo paymentInfo,
                             Map<String, BigDecimal> appliedDiscounts);
}
