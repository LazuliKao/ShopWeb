package com.lazulikao.shop.shopbackend.services;
import com.lazulikao.shop.shopbackend.entity.User;
import com.lazulikao.shop.shopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public boolean existsUser(String username) {
        return userRepository.existsUserByUser(username);
    }
    public Optional<User> tryFindUser(String username, String passwordMd5) {
        User user = new User();
        user.setUser(username);
        user.setPasswordMd5(passwordMd5);
        if (userRepository.exists(Example.of(user))) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }
}