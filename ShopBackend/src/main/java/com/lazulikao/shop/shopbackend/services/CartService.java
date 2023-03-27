package com.lazulikao.shop.shopbackend.services;
import com.lazulikao.shop.shopbackend.entity.CartItem;
import com.lazulikao.shop.shopbackend.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    public List<CartItem> getByUser(String user) {
        return cartRepository.findByUser(user);
    }
    public boolean setToCart(String user, Long id, int count) {
        var item = cartRepository.findByUserAndShopItemId(user, id);
        if (item.isEmpty()) {
            return false;
        } else {
            var oldItem = item.get();
            oldItem.setCount(count);
            cartRepository.save(oldItem);
        }
        return true;
    }
    public boolean addToCart(String user, Long id, int count) {
        //返回指定用户的购物车中指定 id 的商品
        var item = cartRepository.findByUserAndShopItemId(user, id);
        if (item.isEmpty()) {
            var newItem = new CartItem();
            newItem.setUser(user);
            newItem.setShopItemId(id);
            newItem.setCount(1);
            cartRepository.save(newItem);
        } else {
            var oldItem = item.get();
            oldItem.setCount(oldItem.getCount() + count);
            cartRepository.save(oldItem);
        }
        return true;
    }
    public boolean DelCart(String user, Long id) {
        var item = cartRepository.findByUserAndShopItemId(user, id);
        if (item.isPresent()) {
            cartRepository.delete(item.get());
            return true;
        }
        return false;
    }
}
