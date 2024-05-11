package com.example.storeops.user.entity;

import com.example.storeops.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

// TODO: Implement the order and product functionality

/**
 * Represents the profile
 * of a user. The user profile
 * should contain all the orders
 * the user has (as well as completed ones)
 */
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    @OneToMany(mappedBy = "userProfile")
    private List<Order> orders;

    // METHODS

    // Adds an order to the user's profile
    public void addOrder(Order order){
        orders.add(order);
        order.setUserProfile(this);
    }

    // Removes an order from the user's order list
    public void removeOrder(Order order){
        orders.remove(order);
        order.setUserProfile(null);
    }
}
