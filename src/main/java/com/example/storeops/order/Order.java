package com.example.storeops.order;

import com.example.storeops.product.Product;
import com.example.storeops.user.entity.UserProfile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Repesents an order of a user.
 * Each order should contain an order ID,
 * the user and the products ordered
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @OneToMany
    private List<Product> productList;

    // Adds a product to the products list
    public void addProduct(Product product){
        productList.add(product);
    }

    // Removes a product from the product list
    public void removeProduct(Product product){
        productList.remove(product);
    }

}
