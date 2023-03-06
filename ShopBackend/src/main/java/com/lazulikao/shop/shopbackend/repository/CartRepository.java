package com.lazulikao.shop.shopbackend.repository;
import com.lazulikao.shop.shopbackend.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(String user);
    Optional<CartItem> findByUserAndShopItemId(String user, Long id);
}