package com.lazulikao.shop.shopbackend.services;
import com.lazulikao.shop.shopbackend.entity.ShopItem;
import com.lazulikao.shop.shopbackend.repository.CartRepository;
import com.lazulikao.shop.shopbackend.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    public List<ShopItem> getShopList() {
        return cartRepository.findAll()
    }
}
