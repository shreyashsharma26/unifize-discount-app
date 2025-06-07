package com.unifize.discount.service.strategy;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class CategoryDiscountStrategy implements DiscountStrategy {

    private static final Map<String, BigDecimal> categoryDiscounts = Map.of(
            "T-shirts", BigDecimal.valueOf(0.10)
    );

    @Override
    public BigDecimal applyDiscount(BigDecimal currentPrice,
                                    List<CartItem> cartItems,
                                    CustomerProfile customer,
                                    PaymentInfo paymentInfo,
                                    Map<String, BigDecimal> appliedDiscounts) {

        BigDecimal totalCategoryDiscount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            String category = cartItem.getProduct().getCategory();

            if (categoryDiscounts.containsKey(category)) {
                BigDecimal itemPrice = cartItem.getProduct().getBasePrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

                BigDecimal discountAmount = itemPrice.multiply(categoryDiscounts.get(category));
                totalCategoryDiscount = totalCategoryDiscount.add(discountAmount);

                appliedDiscounts.put("Category Discount on " + category, discountAmount);
            }
        }

        return totalCategoryDiscount;
    }

}
