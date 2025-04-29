package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequest;
import com.openclassrooms.mddapi.dto.request.CreatePostRequest;
import com.openclassrooms.mddapi.dto.response.CommentResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostWithCommentsResponseDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;

import java.util.List;

public interface IPostService {
    List<PostResponseDTO> getAllPostsSorted(String order);
    PostResponseDTO getPostById(Long id);
    PostResponseDTO createPost(CreatePostRequest request, Long authorId);
    CommentResponseDTO commentOnPost(Long postId, CreateCommentRequest request, Long authorId);
    PostWithCommentsResponseDTO getPostWithCommentsById(Long id);
}