package com.lazulikao.shop.shopbackend;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/identity")
public class InentityController {
    @GetMapping("/login")
    @ResponseBody
    public LoginResult login(String username, String password) {
        return  new LoginResult(true, "login success" + username);
    }
    private record LoginResult(boolean success, String message) {}
}
