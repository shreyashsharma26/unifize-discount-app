package com.unifize.discount;

import com.unifize.discount.config.TestData;
import com.unifize.discount.config.TestDataConfig;
import com.unifize.discount.model.CustomerProfile;
import com.unifize.discount.model.DiscountedPrice;
import com.unifize.discount.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DiscountServiceImplTest {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private TestDataConfig testDataConfig;

    private TestData testData;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        testData = testDataConfig.testData();
    }

    @Test
    void testCalculateCartDiscounts() {
        DiscountedPrice discountedPrice = discountService.calculateCartDiscounts(
                testData.cartItems,
                testData.customerProfile,
                testData.paymentInfo,
                Optional.empty()
        );

        assertNotNull(discountedPrice);
        assertEquals(2, testData.cartItems.size());
        assertTrue(discountedPrice.getOriginalPrice().compareTo(discountedPrice.getFinalPrice()) > 0);
        assertTrue(discountedPrice.getAppliedDiscounts().size() >= 3); // Brand, Category, Voucher, Bank

        System.out.println("Final price: " + discountedPrice.getFinalPrice());
        System.out.println("Applied discounts: " + discountedPrice.getAppliedDiscounts());
    }

    @Test
    void testValidateValidDiscountCode() {
        boolean isValid = discountService.validateDiscountCode(
                "SUPER69",
                testData.cartItems,
                testData.customerProfile
        );
        assertTrue(isValid);
    }

    @Test
    void testValidateInvalidDiscountCode() {
        boolean isValid = discountService.validateDiscountCode(
                "INVALID_CODE",
                testData.cartItems,
                testData.customerProfile
        );
        assertFalse(isValid);
    }

    @Test
    void testValidateDiscountCodeFailsDueToCustomerTier() {
        CustomerProfile customer = CustomerProfile.builder()
                .id("VOUCHER_USER")
                .tier("SILVER")
                .build();

        boolean isValid = discountService.validateDiscountCode(
                "WELCOME10", //expired code
                testData.cartItems,
                customer
        );

        assertFalse(isValid);
    }
}
