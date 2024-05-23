package com.project.mediumapp.controllers;

import com.project.mediumapp.entities.Comment;
import com.project.mediumapp.requests.CommentCreateRequest;
import com.project.mediumapp.requests.CommentRequest;
import com.project.mediumapp.requests.CommentUpdateRequest;
import com.project.mediumapp.services.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this. commentService= commentService;
    }
    @GetMapping

    public List<CommentRequest> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        return commentService.getAllPosts(userId,postId);
    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId){
        return commentService.getOneComment(commentId);
    }

    @PostMapping
    public Comment createOneComment(@RequestBody CommentCreateRequest newCommentRequest){
        return commentService.createOneComment(newCommentRequest);
    }

    @PutMapping ("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest updateCommentRequest){
        return commentService.updateOneComment(commentId,updateCommentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId){
        commentService.deleteOneComment(commentId);
    }
}
