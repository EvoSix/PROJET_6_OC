package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.response.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    CommentResponseDTO toDto(Comment comment);
}
