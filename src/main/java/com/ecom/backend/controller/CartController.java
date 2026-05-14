package com.ecom.backend.controller;

import com.ecom.backend.entity.CartItem;
import com.ecom.backend.repository.CartItemRepository;
import com.ecom.backend.repository.UserRepository;
import com.ecom.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired private CartItemRepository cartRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ProductRepository productRepo;

    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {
        return cartRepo.findByUser_Id(userId);
    }

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody CartItem item) {
        return cartRepo.save(item);
    }

    @DeleteMapping("/{id}")
    public void removeFromCart(@PathVariable Long id) {
        cartRepo.deleteById(id);
    }

    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartRepo.deleteByUser_Id(userId);
    }
}