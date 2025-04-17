package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.response.UserResponseDTO;
import com.openclassrooms.mddapi.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDto(User user);
}