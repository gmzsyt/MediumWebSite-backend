package com.project.mediumapp.controllers;

import com.project.mediumapp.entities.Post;
import com.project.mediumapp.repositories.PostRepository;
import com.project.mediumapp.requests.PostCreateRequest;
import com.project.mediumapp.requests.PostRequest;
import com.project.mediumapp.requests.PostUpdateRequest;
import com.project.mediumapp.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }
    @GetMapping

    public List<PostRequest> getAllPosts(@RequestParam Optional<Long> userId) {
        return postService.getAllPosts(userId);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostRequest> getOnePost (@PathVariable Long postId){
        Post post = postService.getOnePostById(postId);
        PostRequest postRequest = postService.convertToPostRequest(post);
        return ResponseEntity.ok(postRequest);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId,@RequestBody PostUpdateRequest updatePostRequest){
        return postService.updateOnePost(postId,updatePostRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
       postService.deleteOnePost(postId);
    }
}
