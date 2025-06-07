package com.unifize.discount.service.strategy;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class VoucherDiscountStrategy implements DiscountStrategy {

    private static final Map<String, BigDecimal> voucherDiscounts = Map.of(
            "SUPER69", BigDecimal.valueOf(0.69),
            "WELCOME10", BigDecimal.valueOf(0.10)
    );

    private String currentVoucherCode = null; // Will be set externally

    // Allow setting voucherCode externally
    public void setVoucherCode(String voucherCode) {
        this.currentVoucherCode = voucherCode;
    }

    @Override
    public BigDecimal applyDiscount(BigDecimal currentPrice,
                                    List<CartItem> cartItems,
                                    CustomerProfile customer,
                                    PaymentInfo paymentInfo,
                                    Map<String, BigDecimal> appliedDiscounts) {

        if (currentVoucherCode == null || currentVoucherCode.isBlank()) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountPercentage = voucherDiscounts.getOrDefault(currentVoucherCode, BigDecimal.ZERO);

        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discountAmount = currentPrice.multiply(discountPercentage);
            appliedDiscounts.put("Voucher " + currentVoucherCode, discountAmount);
            return discountAmount;
        }

        return BigDecimal.ZERO;
    }

}
