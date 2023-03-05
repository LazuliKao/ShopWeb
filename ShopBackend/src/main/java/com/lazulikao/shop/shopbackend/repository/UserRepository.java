package com.lazulikao.shop.shopbackend.repository;
import com.lazulikao.shop.shopbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
}