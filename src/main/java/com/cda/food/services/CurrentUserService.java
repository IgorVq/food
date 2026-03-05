package com.cda.food.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cda.food.entities.User;
import com.cda.food.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "User not authenticated");
        }

        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            throw new ResponseStatusException(UNAUTHORIZED, "Authenticated user not found");
        }
        return user;
    }

    public boolean isAdmin() {
        return getCurrentUser().getRole() == User.Role.ADMIN;
    }
}
