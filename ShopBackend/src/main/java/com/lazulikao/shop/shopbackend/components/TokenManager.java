package com.lazulikao.shop.shopbackend.components;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Component
public class TokenManager {
    //token缓存
    private Map<String, String> tokenCache = new HashMap<>();
    // 随机生成token
    private String newToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    // 生成或刷新指定用户的token
    public String generateToken(String username) {
        // 生成随机token
        if (tokenCache.containsKey(username)) {
            //刷新token
            var tk = newToken();
            tokenCache.replace(username, tk);
            System.out.println("刷新token" + username + " " + tk);
            return tk;
        } else {
            //生成token
            var tk = tokenCache.put(username, newToken());
            System.out.println("生成token" + username + " " + tk);
            return tokenCache.get(username);
        }
    }
    // 验证token
    public boolean verifyToken(String token) {
        return tokenCache.containsValue(token);
    }
    // 从token中获取用户名
    public Optional<String> getUserNameFromToken(String token) {
        // 验证token是否有效
        if (tokenCache.containsValue(token)) {
            // 遍历token缓存，找到对应的用户名
            for (var entry : tokenCache.entrySet()) {
                if (entry.getValue().equals(token)) {
                    return Optional.of(entry.getKey());
                }
            }
        }
        return Optional.empty();
    }
    // 删除token
    public void deleteToken(String token) {
        // 删除无效的token
        tokenCache.values().removeIf(t -> t.equals(token));
    }
}
