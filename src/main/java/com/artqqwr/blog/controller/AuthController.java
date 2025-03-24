package com.artqqwr.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.artqqwr.blog.model.User;
import com.artqqwr.blog.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class AuthController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("user", new User());
    return "registration";
  }

  @PostMapping("/signup")
  public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
    if (userRepository.findByUsername(user.getUsername()) != null) {
      bindingResult.rejectValue("username", "error.user", "Username is already taken");
    }
    if (userRepository.findByEmail(user.getEmail()) != null) {
      bindingResult.rejectValue("email", "error.user", "Email is already registered");
    }
    if (bindingResult.hasErrors()) {
      return "registration";
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return "redirect:/login";
  }

  @GetMapping("/login")
  public String login(Authentication authentication) {
    return "login";
  }
}
