package com.artqqwr.blog.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.artqqwr.blog.exception.PostNotFoundException;
import com.artqqwr.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.artqqwr.blog.model.Post;
import com.artqqwr.blog.model.User;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public String listPosts(Model model) {
        List<Post> posts = postService.findAllByOrderByCreatedAtDesc();
        model.addAttribute("posts", posts);
        return "post_list";
    }

    @GetMapping("/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "post_form";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute Post post, @AuthenticationPrincipal User user) {
        post.setAuthor(user);

        if (post.getId() == null)
            post.setCreatedAt(LocalDateTime.now());

        postService.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        try {
            if (!postService.canUserManagePost(id, user.getId()))
                return "redirect:/posts?error=Access%20denied";

            var post = postService.findById(id);
            model.addAttribute("post", post);
            return "post_form";
        } catch (PostNotFoundException e) {
            return "redirect:/posts";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal User user) {
        try {
            var post = postService.findById(id);
            if (!postService.canUserManagePost(id, user.getId())) return "redirect:/posts?error=Access%20denied";

            postService.delete(post.getId());
            return "redirect:/posts";
        } catch (PostNotFoundException e) {
            return "redirect:/posts?error=Post%20not%founded";
        }
    }
}
