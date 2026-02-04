package com.cda.food.dtos;

import com.cda.food.entities.User;

public record UserRequestDTO (
    String firstName,
    String lastName,
    String email,
    String password,
    String phone,
    User.Role role
) {}
