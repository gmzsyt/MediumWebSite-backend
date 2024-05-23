package com.project.mediumapp.services;

import com.project.mediumapp.entities.Post;
import com.project.mediumapp.entities.User;
import com.project.mediumapp.repositories.PostRepository;
import com.project.mediumapp.requests.PostCreateRequest;
import com.project.mediumapp.requests.PostRequest;
import com.project.mediumapp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;
    public PostService(PostRepository postRepository,UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<PostRequest> getAllPosts(Optional<Long> userId) {
        List<Post> posts;

        if (userId.isPresent()){
            posts = postRepository.findByUserId(userId.get());
        } else {
            posts = postRepository.findAll();
        }

        return posts.stream()
                .map(this::convertToPostRequest) // her bir post nesnesini aşağıdaki metoda geçirir ve sonuçları bir liste olarak toplar.
                .collect(Collectors.toList());
    }
     public PostRequest convertToPostRequest(Post post) {
        PostRequest postRequest = new PostRequest();
        postRequest.setId(post.getId());
        postRequest.setTitle(post.getTitle());
        postRequest.setText(post.getText());
        postRequest.setUserName(post.getUser().getUserName());
        return postRequest;
    }


    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }


    public Post createOnePost(PostCreateRequest newPostRequest) {
        User user =userService.getOneUser(newPostRequest.getUserId());
        if(user == null)
            return null;

        Post toSave = new Post();
        toSave.setId(newPostRequest.getId());
        toSave.setText(newPostRequest.getText());
        toSave.setTitle(newPostRequest.getTitle());
        toSave.setUser(user);
        return postRepository.save(toSave);
    }


    public Post updateOnePost(Long postId, PostUpdateRequest updatePostRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()){
            Post toUpdate= post.get(); //post nesnesi optional ile sarmalandigi için direkt setter kullanılamaz
            toUpdate.setText(updatePostRequest.getTitle());
            toUpdate.setText(updatePostRequest.getText());
            return postRepository.save(toUpdate);
        }
        return null;
    }

    public void deleteOnePost(Long postId) {
         postRepository.deleteById(postId);
    }
}
