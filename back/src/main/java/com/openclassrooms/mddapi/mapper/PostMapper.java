package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.response.PostResponseDTO;
import com.openclassrooms.mddapi.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "topic.label", target = "topicLabel")
    @Mapping(source = "author.username", target = "authorUsername")
    PostResponseDTO toDto(Post post);

    List<PostResponseDTO> toDtoList(List<Post> posts);
}