package com.unifize.discount.service;

import com.unifize.discount.exception.DiscountCalculationException;
import com.unifize.discount.exception.DiscountValidationException;
import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.DiscountedPrice;
import com.unifize.discount.model.PaymentInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for calculating discounts on cart items.
 * <p>
 * Discount calculation order:
 * <ol>
 *     <li>Brand discount</li>
 *     <li>Category discount</li>
 *     <li>Voucher discount</li>
 *     <li>Bank offer</li>
 * </ol>
 *
 * Provides also method to validate discount codes.
 *
 * Designed to follow Strategy Pattern for modular discount logic.
 * </p>
 */
public interface DiscountService {
    /**
     * Calculate final price after applying discount logic:
     * - First apply brand/category discounts
     * - Then apply coupon codes
     * - Then apply bank offers
     *
     * @param cartItems List of items in the cart
     * @param customer Customer profile information
     * @param paymentInfo Optional payment information
     * @param voucherCode Optional voucher code
     * @return Calculated discounted price details
     * @throws DiscountCalculationException if calculation fails
     */
    DiscountedPrice calculateCartDiscounts(List<CartItem> cartItems, CustomerProfile customer,
            PaymentInfo paymentInfo, Optional<String> voucherCode) throws DiscountCalculationException;

    boolean validateDiscountCode(String code, List<CartItem> cartItems, CustomerProfile customer)
            throws DiscountValidationException;
}
