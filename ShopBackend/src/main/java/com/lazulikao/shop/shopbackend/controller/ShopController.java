package com.lazulikao.shop.shopbackend.controller;
import com.lazulikao.shop.shopbackend.components.TokenManager;
import com.lazulikao.shop.shopbackend.entity.ShopItem;
import com.lazulikao.shop.shopbackend.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private TokenManager tokenManager;
    private final ShopService shopService;
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }
    private record TokenRequest(String token) {}
    private record GetAllResult(boolean success, String message, Iterable<ShopItem> items) {}
    //获取商品列表
    @PostMapping("/all")
    @ResponseBody
    public GetAllResult getShopList(@RequestBody TokenRequest request) {
        if (request.token == null || request.token.isEmpty() || !tokenManager.verifyToken(request.token)) {
            return new GetAllResult(false, "token 无效", null);
        }
        var all = shopService.getShopList();
        return new GetAllResult(true, "获取成功，共计 " + all.size() + "个", all);
    }
    private record AddToCartResult(boolean success, String message) {}
    @PostMapping("/addtocart")
    @ResponseBody
    public AddToCartResult addToCart(@RequestBody AddToCartRequest request) {
        if (request.token == null || request.token.isEmpty() || !tokenManager.verifyToken(request.token)) {
            return new AddToCartResult(false, "token 无效");
        }


    }
    private record AddToCartRequest(String token, int id, int count) {}
}
