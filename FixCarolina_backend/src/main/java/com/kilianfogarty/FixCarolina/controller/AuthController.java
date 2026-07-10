package com.kilianfogarty.FixCarolina.controller;

import com.kilianfogarty.FixCarolina.dto.AuthResponse;
import com.kilianfogarty.FixCarolina.service.AuthService;
import com.kilianfogarty.FixCarolina.dto.LoginRequest;
import com.kilianfogarty.FixCarolina.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    authService.register(request.username(), request.password());
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    String token = authService.login(request.username(), request.password());
    return ResponseEntity.ok(new AuthResponse(token));
  }
}
