package com.example.storeops.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRegistrationDto {

    private String productName;

    private float productPrice;

    // TODO: Implement category to improve queries
    // private Category category;

}
