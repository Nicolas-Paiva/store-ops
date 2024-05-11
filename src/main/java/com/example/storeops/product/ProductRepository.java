package com.example.storeops.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // TODO: Add a way to retrieve "featured" products

    // TODO (REFACTOR): Add pagination
}

