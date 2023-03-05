package com.lazulikao.shop.shopbackend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class CartItem {
    @Id
    private String User;
    private Long id;
    private int count;
}
