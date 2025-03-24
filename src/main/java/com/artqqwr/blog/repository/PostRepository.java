package com.artqqwr.blog.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.artqqwr.blog.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findAllByOrderByCreatedAtDesc();
}
