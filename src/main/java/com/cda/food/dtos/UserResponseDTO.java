package com.cda.food.dtos;

import com.cda.food.entities.User;

public record UserResponseDTO (
    Integer id,
    String firstName,
    String lastName,
    String email,
    String phone,
    User.Role role
) {}
