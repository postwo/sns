package com.example.boardtest.repository;


import com.example.boardtest.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByUsername(String username);

}
