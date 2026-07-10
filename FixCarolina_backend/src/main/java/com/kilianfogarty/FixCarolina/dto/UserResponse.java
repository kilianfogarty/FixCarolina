package com.kilianfogarty.FixCarolina.dto;

import com.kilianfogarty.FixCarolina.model.User;

public record UserResponse(Long id, String username) {
  public static UserResponse from(User user) {
    return new UserResponse(user.getId(), user.getUsername());
  }
}
