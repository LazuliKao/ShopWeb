package com.lazulikao.shop.shopbackend.controller;
import com.lazulikao.shop.shopbackend.components.TokenManager;
import com.lazulikao.shop.shopbackend.entity.ShopItem;
import com.lazulikao.shop.shopbackend.repository.CartRepository;
import com.lazulikao.shop.shopbackend.services.CartService;
import com.lazulikao.shop.shopbackend.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/shop")
public class ShopController {
    private final TokenManager tokenManager;
    private final CartService cartService;
    private final ShopService shopService;
    public ShopController(ShopService shopService, TokenManager tokenManager, CartService cartService) {
        this.shopService = shopService;
        this.tokenManager = tokenManager;
        this.cartService = cartService;
    }
    private record TokenRequest(String token) {}
    private record GetAllResult(boolean success, String message, Iterable<ShopItem> items) {}
    //获取商品列表
    @PostMapping("/all")
    @ResponseBody
    public GetAllResult getShopList(@RequestBody TokenRequest request) {
        if ( request.token.isEmpty() || !tokenManager.verifyToken(request.token)) {
            return new GetAllResult(false, "token 无效", null);
        }
        var all = shopService.getShopList();
        return new GetAllResult(true, "获取成功，共计 " + all.size() + "个", all);
    }
    @PostMapping("/addtocart")
    @ResponseBody
    public AddToCartResult addToCart(@RequestBody AddToCartRequest request) {
        var user = tokenManager.getUserNameFromToken(request.token);
        if (user.isEmpty()) {
            return new AddToCartResult(false, "token 无效");
        }
        var success = cartService.addToCart(user.get(), request.id, request.count);
        if (success) {
            return new AddToCartResult(true, "添加成功");
        }
        return new AddToCartResult(false, "添加失败");
    }
    private record AddToCartRequest(String token, Long id, int count) {}
    private record AddToCartResult(boolean success, String message) {}
    @PostMapping("/settocart")
    @ResponseBody
    public SetToCartResult setToCart(@RequestBody SetToCartRequest request) {
        var user = tokenManager.getUserNameFromToken(request.token);
        if (user.isEmpty()) {
            return new SetToCartResult(false, "token 无效");
        }
        var success = cartService.setToCart(user.get(), request.id, request.count);
        if (success) {
            return new SetToCartResult(true, "修改成功");
        }
        return new SetToCartResult(false, "修改失败");
    }
    private record SetToCartRequest(String token, Long id, int count) {}
    private record SetToCartResult(boolean success, String message) {}
    @PostMapping("/delcart")
    @ResponseBody
    public DelCartResult DelCart(@RequestBody DelCartRequest request) {
        var user = tokenManager.getUserNameFromToken(request.token);
        if (user.isEmpty()) {
            return new DelCartResult(false, "token 无效");
        }
        var success = cartService.DelCart(user.get(), request.id);
        if (success) {
            return new DelCartResult(true, "删除成功");
        }
        return new DelCartResult(false, "删除失败");
    }
    private record DelCartRequest(String token, Long id) {}
    private record DelCartResult(boolean success, String message) {}
    @PostMapping("/getcart")
    @ResponseBody
    private GetCartResult GetCart(@RequestBody TokenRequest request) {
        var user = tokenManager.getUserNameFromToken(request.token);
        if (user.isEmpty()) {
            return new GetCartResult(false, "token 无效", null);
        }
        var cart = cartService.getByUser(user.get());
        var result = new java.util.ArrayList<ShopItem>();
        for (var item : cart) {//通过ID查询具体信息
            var shopItem = shopService.getById(item.getShopItemId());
            if (shopItem.isEmpty()) {
                System.out.println("商品不存在: " + item.getShopItemId());
                continue;
            }
            shopItem.get().setCount(item.getCount());
            result.add(shopItem.get());
        }
        return new GetCartResult(true, "获取成功，共计 " + cart.size() + "个", result);
    }
    private record GetCartResult(boolean success, String message, Iterable<ShopItem> items) {}
}
