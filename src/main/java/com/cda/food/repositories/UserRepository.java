package com.cda.food.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cda.food.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
