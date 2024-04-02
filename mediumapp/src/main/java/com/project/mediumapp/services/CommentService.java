package com.project.mediumapp.services;

import com.project.mediumapp.entities.Comment;
import com.project.mediumapp.entities.Post;
import com.project.mediumapp.entities.User;
import com.project.mediumapp.repositories.CommentRepository;
import com.project.mediumapp.requests.CommentCreateRequest;
import com.project.mediumapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private  UserService userService;
    private  PostService postService;

    public CommentService(CommentRepository commentRepository, UserService userService, PostService postService){
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Comment> getAllPosts(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent())
            return commentRepository.findByUserIdAndPostId(userId.get(),postId.get());
        if (userId.isPresent())
            return commentRepository.findByUserId(userId.get()); //userId optional oldugu için optional classının get metodu kullanılır.
        else if (postId.isPresent()) {
            return commentRepository.findByPostId(postId.get());
        }
        else return commentRepository.findAll();
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user = userService.getOneUser(newCommentRequest.getUserId());
        Post post = postService.getOnePostById(newCommentRequest.getPostId());
        if(post ==null || user == null) return null;
        Comment comment = new Comment();
        comment.setId(newCommentRequest.getId());
        comment.setText(newCommentRequest.getText());
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public Comment updateOneComment(Long commentId, CommentUpdateRequest updateCommentRequest) {
        Comment comment = getOneComment(commentId);
        if (comment == null) return null;
        comment.setText(updateCommentRequest.getText());
        return commentRepository.save(comment);
    }

    public void deleteOneComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
