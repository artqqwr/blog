package com.artqqwr.blog.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artqqwr.blog.model.Post;
import com.artqqwr.blog.model.User;
import com.artqqwr.blog.repository.PostRepository;
import com.artqqwr.blog.repository.UserRepository;

@Controller
@RequestMapping("/posts")
public class PostController {
  @Autowired
  private PostRepository postRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @GetMapping
  public String listPosts(Model model) {
    List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
    model.addAttribute("posts", posts);
    return "post_list";
  }
  
  @GetMapping("/new")
  public String newPost(Model model) {
    model.addAttribute("post", new Post());
    return "post_form";
  }
  
  @PostMapping("/save")
  public String savePost(@ModelAttribute Post post, Principal principal) {
    String username = principal.getName();
    User author = userRepository.findByUsername(username);
    post.setAuthor(author);
    if (post.getId() == null) {
      post.setCreatedAt(LocalDateTime.now());
    }
    postRepository.save(post);
    return "redirect:/posts";
  }
  
  @GetMapping("/edit/{id}")
  public String editPost(@PathVariable Long id, Model model, Principal principal) {
    Optional<Post> postOpt = postRepository.findById(id);
    if (postOpt.isPresent()) {
      Post post = postOpt.get();
      if (!post.getAuthor().getUsername().equals(principal.getName())) {
        return "redirect:/posts?error=Access%20denied";
      }
      model.addAttribute("post", post);
      return "post_form";
    }
    return "redirect:/posts";
  }
  
  @GetMapping("/delete/{id}")
  public String deletePost(@PathVariable Long id, Principal principal) {
    Optional<Post> postOpt = postRepository.findById(id);
    if (postOpt.isPresent()) {
      Post post = postOpt.get();
      if (post.getAuthor().getUsername().equals(principal.getName())) {
        postRepository.delete(post);
      }
    }
    return "redirect:/posts";
  }
}
