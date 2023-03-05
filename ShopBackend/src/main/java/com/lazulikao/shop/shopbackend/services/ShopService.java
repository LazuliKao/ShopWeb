package com.lazulikao.shop.shopbackend.services;
import com.lazulikao.shop.shopbackend.entity.ShopItem;
import com.lazulikao.shop.shopbackend.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShopService {
    private final ShopRepository shopRepository;
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }
    public List<ShopItem> getShopList() {
        return shopRepository.findAll();
    }
}
