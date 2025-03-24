package com.artqqwr.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artqqwr.blog.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);
  User findByEmail(String email);
}
