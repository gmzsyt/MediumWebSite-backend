package com.project.mediumapp.services;

import com.project.mediumapp.entities.Comment;
import com.project.mediumapp.entities.Post;
import com.project.mediumapp.entities.User;
import com.project.mediumapp.repositories.CommentRepository;
import com.project.mediumapp.requests.CommentCreateRequest;
import com.project.mediumapp.requests.CommentRequest;
import com.project.mediumapp.requests.CommentUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<CommentRequest> getAllPosts(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comment;
        if(userId.isPresent() && postId.isPresent()) {
            comment = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            comment = commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            comment = commentRepository.findByPostId(postId.get());
        } else {
            comment = commentRepository.findAll();
        }
        return comment.stream()
                .map(this::convertToCommentRequest)
                .collect(Collectors.toList());
    }

    public CommentRequest convertToCommentRequest(Comment comment){
        CommentRequest commentRequest=new CommentRequest();
        commentRequest.setId(comment.getId());
        commentRequest.setText(comment.getText());
        commentRequest.setUserName(comment.getUser().getUserName());
        return commentRequest;
    }

    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    public Comment createOneComment(CommentCreateRequest newCommentRequest) {
        User user = userService.getOneUser(newCommentRequest.getUserId());
        Post post = postService.getOnePostById(newCommentRequest.getPostId());

        if (post == null || user == null) return null;

        Comment comment = new Comment();
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
