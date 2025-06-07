package com.unifize.discount.service.strategy;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class VoucherData {
    private BigDecimal discountPercentage;
    private LocalDate expirationDate;
}