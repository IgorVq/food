package com.cda.food.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cda.food.config.JwtUtil;
import com.cda.food.config.PasswordUtil;
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
        User user = userMappers.toEntity(userRequestDTO);
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updatedUser(Integer id, UserRequestDTO userRequestDTO) {
        User userToUpdate = userMappers.toEntity(userRequestDTO);
        userToUpdate.setId(id);
        userToUpdate.setPassword(PasswordUtil.encode(userToUpdate.getPassword()));
        userRepository.save(userToUpdate);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    public String login(User user) {
        User currentUser = userRepository.findByEmail(user.getEmail());
        if (currentUser != null && PasswordUtil.matches(user.getPassword(), currentUser.getPassword())) {
            return JwtUtil.generateToken(currentUser.getEmail(), currentUser.getRole().name());
        }
        return null;
    }
}
