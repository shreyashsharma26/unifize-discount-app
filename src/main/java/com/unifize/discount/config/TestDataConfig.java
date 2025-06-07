package com.unifize.discount.config;

import com.unifize.discount.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class TestDataConfig {

    @Bean
    public TestData testData() {
        Product pumaTshirt = Product.builder()
                .id("PUMA-TSHIRT-01")
                .brand("PUMA")
                .brandTier(BrandTier.PREMIUM)
                .category("T-shirts")
                .basePrice(BigDecimal.valueOf(1000))
                .build();

        Product adidasShoes = Product.builder()
                .id("ADIDAS-SHOES-01")
                .brand("ADIDAS")
                .brandTier(BrandTier.LUXURY)
                .category("Shoes")
                .basePrice(BigDecimal.valueOf(5000))
                .build();

        CartItem cartItem1 = CartItem.builder()
                .product(pumaTshirt)
                .quantity(1)
                .size("M")
                .build();

        CartItem cartItem2 = CartItem.builder()
                .product(adidasShoes)
                .quantity(2)
                .size("42")
                .build();

        CustomerProfile customerProfile = CustomerProfile.builder()
                .id("VOUCHER_USER")
                .tier("GOLD")
                .build();

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .method("CARD")
                .bankName("ICICI")
                .cardType("CREDIT")
                .build();

        return new TestData(List.of(cartItem1, cartItem2), customerProfile, paymentInfo);
    }


}
