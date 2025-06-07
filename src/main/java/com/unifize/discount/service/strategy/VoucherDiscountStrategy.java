package com.unifize.discount.service.strategy;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class VoucherDiscountStrategy implements DiscountStrategy {

    private static final Map<String, VoucherData> voucherDataMap = Map.of(
            "SUPER69", new VoucherData(
                    BigDecimal.valueOf(0.69),
                    LocalDate.of(2025, 12, 31)
            ),
            "WELCOME10", new VoucherData(
                    BigDecimal.valueOf(0.10),
                    LocalDate.of(2024, 7, 31)
            )
    );

    private String currentVoucherCode = null;

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

        VoucherData voucherData = voucherDataMap.get(currentVoucherCode);

        if (voucherData == null) {
            return BigDecimal.ZERO;
        }

        // Check expiration date
        if (voucherData.getExpirationDate().isBefore(LocalDate.now())) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountAmount = currentPrice.multiply(voucherData.getDiscountPercentage());
        appliedDiscounts.put("Voucher " + currentVoucherCode, discountAmount);
        return discountAmount;
    }

    // Optional: You can also expose a public validateVoucherCode(code) method here
    public boolean isValidVoucher(String code) {
        VoucherData voucherData = voucherDataMap.get(code);
        return voucherData != null && !voucherData.getExpirationDate().isBefore(LocalDate.now());
    }

}
