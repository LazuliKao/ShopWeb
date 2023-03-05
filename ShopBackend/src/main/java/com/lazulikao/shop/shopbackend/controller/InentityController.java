package com.lazulikao.shop.shopbackend.controller;
import com.lazulikao.shop.shopbackend.entity.User;
import com.lazulikao.shop.shopbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/identity")
public class InentityController {
    private final UserService userService;
    @Autowired
    public InentityController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    @ResponseBody
    public LoginResult login(String username, String password) {
        //find user in database
        User user = userService.findUserByName(username);
        return new LoginResult(true, "login success" + username);
    }
    private record LoginResult(boolean success, String message) {}
    @GetMapping("/register")
    @ResponseBody
    public RegisterResult register(String username, String email, String passwordMD5) {
        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPasswordMD5(passwordMD5);
        userService.saveUser(user);
        System.out.println("register success" + username);
        return new RegisterResult(true, "register success" + username);
    }
    private record RegisterResult(boolean success, String message) {}
}
