package com.lazulikao.shop.shopbackend.repository;
import com.lazulikao.shop.shopbackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CartRepository extends JpaRepository<CartItem, Long> {
}