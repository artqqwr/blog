package com.artqqwr.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artqqwr.blog.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
}
