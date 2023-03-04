package com.lazulikao.shop.shopbackend.services;
import com.lazulikao.shop.shopbackend.entity.User;
import com.lazulikao.shop.shopbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
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
    public User findUserByName(String username) {
          return userRepository.findOne(Example.of(new User())).orElse(null);
    }
}