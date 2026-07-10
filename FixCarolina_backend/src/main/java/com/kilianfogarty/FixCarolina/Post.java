package com.kilianfogarty.FixCarolina;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(nullable = false)
  private String imagePath;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String locationText;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  @Column(nullable = false, updatable = false)
  private Instant creationTime;

  public Post(User user, String imagePath, String description, String locationText) {
    this.user = user;
    this.imagePath = imagePath;
    this.description = description;
    this.locationText = locationText;
  }

  @PrePersist
  private void onCreation() {
    this.creationTime = Instant.now();
    if (this.status == null) {
      this.status = Status.PENDING;
    }
  }
}
