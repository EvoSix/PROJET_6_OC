package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequest;
import com.openclassrooms.mddapi.dto.request.CreatePostRequest;
import com.openclassrooms.mddapi.dto.response.CommentResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostWithCommentsResponseDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.IPostService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final IPostService postService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(@RequestParam(defaultValue = "desc") String order) {
        return ResponseEntity.ok(postService.getAllPostsSorted(order));
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<PostWithCommentsResponseDTO> getPostById(@PathVariable Long id) {

            return ResponseEntity.ok(postService.getPostWithCommentsById(id));

    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody CreatePostRequest request) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postService.createPost(request, email));

    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentResponseDTO> commentOnPost(@PathVariable Long id, @RequestBody CreateCommentRequest request) {

            String email = SecurityContextHolder.getContext().getAuthentication().getName();


            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postService.commentOnPost(id, request, email));

    }
}