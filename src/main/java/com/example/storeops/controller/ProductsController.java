package com.example.storeops.controller;

import com.example.storeops.product.ProductRepository;
import com.example.storeops.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductsController {


    // TODO: Controller for displaying all the products
    // TODO: Implemet some pagination to display a limited number of products

    @GetMapping("/test")
    public ResponseEntity<?> saveProduct(Authentication authentication){

        // Checking whether the user is an admin
        boolean isAdmin;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return ResponseEntity.ok(authorities);
    }

//    @GetMapping("/all-products")
//    public ResponseEntity<?> getFeaturedProducts(){
//
//    }

}
