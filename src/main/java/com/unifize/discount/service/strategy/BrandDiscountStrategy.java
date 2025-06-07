package com.unifize.discount.service.strategy;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class BrandDiscountStrategy implements DiscountStrategy {

    private static final Map<String, BigDecimal> brandDiscounts = Map.of(
            "PUMA", BigDecimal.valueOf(0.40)
    );

    @Override
    public BigDecimal applyDiscount(BigDecimal currentPrice,
                                    List<CartItem> cartItems,
                                    CustomerProfile customer,
                                    PaymentInfo paymentInfo,
                                    Map<String, BigDecimal> appliedDiscounts) {

        BigDecimal totalBrandDiscount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            String brand = cartItem.getProduct().getBrand();

            if (brandDiscounts.containsKey(brand)) {
                BigDecimal itemPrice = cartItem.getProduct().getBasePrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

                BigDecimal discountAmount = itemPrice.multiply(brandDiscounts.get(brand));
                totalBrandDiscount = totalBrandDiscount.add(discountAmount);

                appliedDiscounts.put("Brand Discount on " + brand, discountAmount);
            }
        }

        return totalBrandDiscount;
    }

}
