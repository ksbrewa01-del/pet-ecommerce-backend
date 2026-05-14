package com.ecom.backend.controller;

import com.ecom.backend.entity.*;
import com.ecom.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired private OrderRepository orderRepo;
    @Autowired private CartItemRepository cartRepo;
    @Autowired private UserRepository userRepo;

    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        List<CartItem> cartItems = cartRepo.findByUser_Id(userId);
        
        Order order = new Order();
        order.setUser(user);
        
        double total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getProduct().getPrice());
            total += ci.getProduct().getPrice() * ci.getQuantity();
            orderItems.add(oi);
        }
        
        order.setTotalAmount(total);
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepo.save(order);
        
        cartRepo.deleteByUser_Id(userId); // Cart clear
        return savedOrder;
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderRepo.findByUser_Id(userId);
    }
}