package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.response.TopicResponseDTO;
import com.openclassrooms.mddapi.model.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {


    TopicResponseDTO toDto(Topic topic);

    List<TopicResponseDTO> toDtoList(List<Topic> topics);
}
