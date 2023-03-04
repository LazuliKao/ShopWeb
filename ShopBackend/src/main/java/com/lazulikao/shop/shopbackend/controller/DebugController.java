package com.lazulikao.shop.shopbackend.controller;
import com.lazulikao.shop.shopbackend.entity.User;
import com.lazulikao.shop.shopbackend.repository.UserRepository;
import com.lazulikao.shop.shopbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/debug")
public class DebugController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/all")
    @ResponseBody
    public Iterable<User> all() {
        return userRepository.findAll();
    }
}

