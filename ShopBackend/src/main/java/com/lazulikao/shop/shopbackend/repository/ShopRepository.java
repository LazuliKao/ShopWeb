package com.lazulikao.shop.shopbackend.repository;
import com.lazulikao.shop.shopbackend.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ShopRepository extends JpaRepository<ShopItem, Long> {
}