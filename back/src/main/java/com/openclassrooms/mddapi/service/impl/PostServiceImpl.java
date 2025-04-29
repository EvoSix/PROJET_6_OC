package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequest;
import com.openclassrooms.mddapi.dto.request.CreatePostRequest;
import com.openclassrooms.mddapi.dto.response.CommentResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostResponseDTO;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.IAuthService;
import com.openclassrooms.mddapi.service.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final IAuthService authService;

    @Override
    public List<PostResponseDTO> getAllPostsSorted(String order) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmailOrUsername(email)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur connecté non trouvé"));



        if (user.getSubscriptions() != null && !user.getSubscriptions().isEmpty()) {

            List<Long> topicIds = user.getSubscriptions().stream()
                    .map(Topic::getId).collect(Collectors.toList());


            List<Post> posts = "asc".equalsIgnoreCase(order)
                    ? postRepository.findByTopicIdInOrderByCreatedAtAsc(topicIds)
                    : postRepository.findByTopicIdInOrderByCreatedAtDesc(topicIds);

            return postMapper.toDtoList(posts);
        } else {

            List<Post> posts = "asc".equalsIgnoreCase(order)
                    ? postRepository.findAllByOrderByCreatedAtAsc()
                    : postRepository.findAllByOrderByCreatedAtDesc();
            return postMapper.toDtoList(posts);
        }






    }

    @Override
    public PostResponseDTO getPostById(Long id) {
        return postMapper.toDto(
                postRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Post non trouvé"))
        );
    }

    @Override
    public PostResponseDTO createPost(CreatePostRequest request, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Auteur non trouvé"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new NoSuchElementException("Sujet non trouvé"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
        post.setTopic(topic);
        post.setCreatedAt(LocalDateTime.now());

        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public CommentResponseDTO commentOnPost(Long postId, CreateCommentRequest request, Long authorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post non trouvé"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Auteur non trouvé"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);

        return commentMapper.toDto(commentRepository.save(comment));
    }
}