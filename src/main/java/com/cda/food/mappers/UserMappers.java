package com.cda.food.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cda.food.dtos.UserRequestDTO;
import com.cda.food.dtos.UserResponseDTO;
import com.cda.food.entities.User;

@Mapper(componentModel = "spring")
public interface UserMappers {
    UserResponseDTO toDto(User user);
    List<UserResponseDTO> toDtoList(List<User> user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipes", ignore = true)
    User toEntity(UserRequestDTO userRequestDTO);
}
