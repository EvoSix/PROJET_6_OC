package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.request.CreateCommentRequest;
import com.openclassrooms.mddapi.dto.request.CreatePostRequest;
import com.openclassrooms.mddapi.dto.response.CommentResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostResponseDTO;
import com.openclassrooms.mddapi.dto.response.PostWithCommentsResponseDTO;
import com.openclassrooms.mddapi.exception.PostNotFoundException;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
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
                postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post non trouvé"))
        );
    }


    @Override
    public PostWithCommentsResponseDTO getPostWithCommentsById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post non trouvé"));

        PostResponseDTO postDto = postMapper.toDto(post);

        List<CommentResponseDTO> comments = commentRepository.findByPostId(post.getId())
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());

        return new PostWithCommentsResponseDTO(postDto, comments);
    }

    @Override
    public PostResponseDTO createPost(CreatePostRequest request, String email) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Auteur non trouvé"));

        Topic topic = topicRepository.findById(request.getTopicId())
                .orElseThrow(() -> new TopicNotFoundException("Sujet non trouvé"));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
        post.setTopic(topic);
        post.setCreatedAt(LocalDateTime.now());

        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public CommentResponseDTO commentOnPost(Long postId, CreateCommentRequest request, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post non trouvé"));

        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Auteur non trouvé"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);

        return commentMapper.toDto(commentRepository.save(comment));
    }

}