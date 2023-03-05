package com.lazulikao.shop.shopbackend.services;
import com.lazulikao.shop.shopbackend.entity.CartItem;
import com.lazulikao.shop.shopbackend.entity.ShopItem;
import com.lazulikao.shop.shopbackend.repository.CartRepository;
import com.lazulikao.shop.shopbackend.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public List<CartItem> getByUser(String user) {
        var sample = new CartItem();
        sample.setUser(user);
        return cartRepository.findAll(Example.of(sample));
    }
    public boolean addToCart(String user, Long id, int count) {
        var sample = new CartItem();
        sample.setUser(user);
        sample.setShopItemId(id);
        var items = cartRepository.findAll(Example.of(sample));
        if (items.isEmpty()) {
            var newItem = new CartItem();
            newItem.setUser(user);
            newItem.setShopItemId(id);
            newItem.setCount(1);
            cartRepository.save(newItem);
        } else {
            var oldItem = items.get(0);
            oldItem.setCount(oldItem.getCount() + count);
            cartRepository.save(oldItem);
        }
        return true;
    }
}
