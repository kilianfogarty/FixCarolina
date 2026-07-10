package com.kilianfogarty.FixCarolina.model;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String hashedPassword;

  @Column(nullable = false, updatable = false)
  private Instant creationTime;

  public User(String username, String hashedPassword) {
    this.username = username;
    this.hashedPassword = hashedPassword;
  }

  @PrePersist
  private void onCreation() {
    this.creationTime = Instant.now();
  }
}
