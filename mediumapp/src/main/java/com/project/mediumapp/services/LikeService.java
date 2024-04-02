package com.project.mediumapp.services;

import com.project.mediumapp.entities.Like;
import com.project.mediumapp.entities.Post;
import com.project.mediumapp.entities.User;
import com.project.mediumapp.repositories.LikeRepository;
import com.project.mediumapp.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        if(userId.isPresent() && postId.isPresent())
            return likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        else if (userId.isPresent())
            return likeRepository.findByUserId(userId.get());
        else if (postId.isPresent()) {
            return likeRepository.findByPostId(postId.get());
        } else return likeRepository.findAll();
    }

    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest newLikeRequest) {
        User user = userService.getOneUser(newLikeRequest.getUserId());
        Post post = postService.getOnePostById(newLikeRequest.getPostId());
        if (user != null && post != null) {
            Like like = new Like();
            like.setId(newLikeRequest.getId());
            like.setPost(post);
            like.setUser(user);
            return likeRepository.save(like);
        }else return null;
    }
        public void deleteOneLike (Long likeId){
        }
    }

