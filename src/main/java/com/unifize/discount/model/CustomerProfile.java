package com.unifize.discount.model;

import lombok.Builder;
import lombok.Data;

/**
 * Customer information used for discount eligibility
 */
@Data
@Builder
public class CustomerProfile {
    private String id;
    private String tier;
}
