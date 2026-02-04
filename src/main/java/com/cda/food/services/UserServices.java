package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.food.dtos.UserRequestDTO;
import com.cda.food.dtos.UserResponseDTO;
import com.cda.food.entities.User;
import com.cda.food.mappers.UserMappers;
import com.cda.food.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;
    private final UserMappers userMappers;

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMappers.toDtoList(users);
    }

    public UserResponseDTO getUserById(Integer id) {
        User user = userRepository.findById(id).isPresent() 
            ? userRepository.findById(id).get() : null;
        return userMappers.toDto(user);
    }

    public void createUser(UserRequestDTO userRequestDTO) {
        userRepository.save(userMappers.toEntity(userRequestDTO));
    }

    public void updatedUser(Integer id, UserRequestDTO userRequestDTO) {
        User userToUpdate = userMappers.toEntity(userRequestDTO);
        userToUpdate.setId(id);
        userRepository.save(userToUpdate);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
