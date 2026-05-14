package com.ecom.backend.repository;

import com.ecom.backend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser_Id(Long userId);
    void deleteByUser_Id(Long userId);
}