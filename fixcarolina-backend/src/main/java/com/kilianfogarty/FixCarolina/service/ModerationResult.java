package com.kilianfogarty.FixCarolina.service;

public record ModerationResult(boolean approved, String reason) {
  public static ModerationResult approve() {
    return new ModerationResult(true, null);
  }

  public static ModerationResult block(String reason) {
    return new ModerationResult(false, reason);
  }
}
