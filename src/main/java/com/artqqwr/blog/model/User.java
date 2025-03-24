package com.artqqwr.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Username cannot be empty")
  private String username;

  @NotBlank(message = "Email cannot be empty")
  @Email(message = "Please provide a valid email")
  private String email;

  @NotBlank(message = "Password cannot be empty")
  @Size(min = 5, message = "Password must be at least 5 characters long")
  private String password;

  private String role = "USER";
}
