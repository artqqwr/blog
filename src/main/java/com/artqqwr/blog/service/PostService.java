package com.artqqwr.blog.service;

import com.artqqwr.blog.exception.PostNotFoundException;
import com.artqqwr.blog.model.Post;
import com.artqqwr.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<Post> findAllByOrderByCreatedAtDesc() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    public boolean canUserManagePost(Long postId, Long userId) {
        var post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not founded")
        );

        return post.getAuthor().getId().equals(userId);
    }



    public void delete(long id) {
        postRepository.deleteById(id);
    }

    public Post findById(long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new PostNotFoundException("Post not founded")
        );
    }

    public void save(Post post) {
        postRepository.save(post);
    }



}
