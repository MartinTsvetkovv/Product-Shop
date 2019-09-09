package com.tsvetkov.productshop.productshop.repository;

import com.tsvetkov.productshop.productshop.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {


    Optional<User> findUserByUsername(String username);
}
