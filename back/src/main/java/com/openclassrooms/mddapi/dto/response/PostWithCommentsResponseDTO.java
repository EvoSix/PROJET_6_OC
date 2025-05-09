package com.openclassrooms.mddapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostWithCommentsResponseDTO {
    private PostResponseDTO post;
    private List<CommentResponseDTO> comments;
}
