package com.unifize.discount.service.strategy;


import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class BankOfferDiscountStrategy implements DiscountStrategy {

    private static final Map<String, BigDecimal> bankOffers = Map.of(
            "ICICI", BigDecimal.valueOf(0.10),
            "HDFC", BigDecimal.valueOf(0.15),
            "SBI", BigDecimal.valueOf(0.05)
    );

    @Override
    public BigDecimal applyDiscount(BigDecimal currentPrice,
                                    List<CartItem> cartItems,
                                    CustomerProfile customer,
                                    PaymentInfo paymentInfo,
                                    Map<String, BigDecimal> appliedDiscounts) {

        if (paymentInfo == null || paymentInfo.getBankName() == null) {
            return BigDecimal.ZERO;
        }

        String bankName = paymentInfo.getBankName();

        BigDecimal discountPercentage = bankOffers.getOrDefault(bankName, BigDecimal.ZERO);

        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discountAmount = currentPrice.multiply(discountPercentage);
            appliedDiscounts.put("Bank Offer (" + bankName + ")", discountAmount);
            return discountAmount;
        }

        return BigDecimal.ZERO;
    }
}
