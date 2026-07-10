package com.kilianfogarty.FixCarolina;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthService(UserRepository userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public void register(String username, String rawPassword) {
    if (userRepo.existsByUsername(username)) {
      throw new IllegalArgumentException("Username already taken.");
    }
    String hashedPassword = passwordEncoder.encode(rawPassword);
    User user = new User(username, hashedPassword);
    userRepo.save(user);
  }

  public String login(String username, String rawPassword) {
    User user =
        userRepo
            .findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

    if (!passwordEncoder.matches(rawPassword, user.getHashedPassword())) {
      throw new IllegalArgumentException("Invalid username or password.");
    }

    return jwtUtil.generateToken(user.getUsername());
  }
}
