package com.unifize.discount.service.strategy;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Strategy interface for applying specific discount types.
 *
 * Implementations must provide logic for:
 * - Calculating discount amount based on current cart state
 * - Recording applied discounts into the provided map
 *
 * This interface supports extensible discount types using the Strategy Pattern.
 */
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
