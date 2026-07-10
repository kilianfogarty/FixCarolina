package com.kilianfogarty.FixCarolina.repository;

import java.util.Optional;

import com.kilianfogarty.FixCarolina.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);
}
