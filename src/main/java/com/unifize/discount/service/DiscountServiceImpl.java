package com.unifize.discount.service;

import com.unifize.discount.exception.DiscountCalculationException;
import com.unifize.discount.exception.DiscountValidationException;
import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.DiscountedPrice;
import com.unifize.discount.model.PaymentInfo;
import com.unifize.discount.service.strategy.BankOfferDiscountStrategy;
import com.unifize.discount.service.strategy.BrandDiscountStrategy;
import com.unifize.discount.service.strategy.CategoryDiscountStrategy;
import com.unifize.discount.service.strategy.VoucherDiscountStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final BrandDiscountStrategy brandDiscountStrategy;
    private final CategoryDiscountStrategy categoryDiscountStrategy;
    private final VoucherDiscountStrategy voucherDiscountStrategy;
    private final BankOfferDiscountStrategy bankOfferDiscountStrategy;

    @Override
    public DiscountedPrice calculateCartDiscounts(List<CartItem> cartItems, CustomerProfile customer, PaymentInfo paymentInfo,
                                                   Optional<String> voucherCode) throws DiscountCalculationException {
        try {
            // Step 1: Calculate original price
            BigDecimal totalOriginalPrice = cartItems.stream()
                    .map(item -> item.getProduct().getBasePrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal currentPrice = totalOriginalPrice;
            Map<String, BigDecimal> appliedDiscounts = new LinkedHashMap<>();

            // Step 2: Apply Brand discount
            BigDecimal brandDiscount = brandDiscountStrategy.applyDiscount(
                    currentPrice, cartItems, customer, paymentInfo, appliedDiscounts);
            currentPrice = currentPrice.subtract(brandDiscount);

            // Step 3: Apply Category discount
            BigDecimal categoryDiscount = categoryDiscountStrategy.applyDiscount(
                    currentPrice, cartItems, customer, paymentInfo, appliedDiscounts);
            currentPrice = currentPrice.subtract(categoryDiscount);

            // Step 4: Apply Voucher discount
            voucherDiscountStrategy.setVoucherCode(voucherCode.orElse(null));
            BigDecimal voucherDiscount = voucherDiscountStrategy.applyDiscount(
                    currentPrice, cartItems, customer, paymentInfo, appliedDiscounts);
            currentPrice = currentPrice.subtract(voucherDiscount);

            // Step 5: Apply Bank Offer discount
            BigDecimal bankDiscount = bankOfferDiscountStrategy.applyDiscount(
                    currentPrice, cartItems, customer, paymentInfo, appliedDiscounts);
            currentPrice = currentPrice.subtract(bankDiscount);

            return DiscountedPrice.builder()
                    .originalPrice(totalOriginalPrice.setScale(2, RoundingMode.HALF_UP))
                    .finalPrice(currentPrice.setScale(2, RoundingMode.HALF_UP))
                    .appliedDiscounts(appliedDiscounts)
                    .message("Discounts Applied Successfully")
                    .build();

        } catch (Exception e) {
            throw new DiscountCalculationException("Failed to calculate cart discounts", e);
        }
    }

    @Override
    public boolean validateDiscountCode(String code, List<CartItem> cartItems, CustomerProfile customer)
            throws DiscountValidationException {
        try {
            return voucherDiscountStrategy.isValidVoucher(code);
        } catch (Exception e) {
            throw new DiscountValidationException("Invalid discount code", e);
        }
    }
}
