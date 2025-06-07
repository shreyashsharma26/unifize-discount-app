package com.unifize.discount.config;

import com.unifize.discount.model.CartItem;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.PaymentInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestData {

    public List<CartItem> cartItems;
    public CustomerProfile customerProfile;
    public PaymentInfo paymentInfo;

    public TestData() {
    }

    public TestData(List<CartItem> cartItems, CustomerProfile customerProfile, PaymentInfo paymentInfo) {
        this.cartItems = cartItems;
        this.customerProfile = customerProfile;
        this.paymentInfo = paymentInfo;
    }
}
