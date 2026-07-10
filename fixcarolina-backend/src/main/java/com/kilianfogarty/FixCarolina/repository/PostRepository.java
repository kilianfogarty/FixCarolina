package com.kilianfogarty.FixCarolina.repository;

import com.kilianfogarty.FixCarolina.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
