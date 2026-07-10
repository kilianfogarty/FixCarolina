package com.kilianfogarty.FixCarolina.controller;

import com.kilianfogarty.FixCarolina.dto.PostResponse;
import com.kilianfogarty.FixCarolina.service.PostService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description,
            @RequestParam("locationText") String locationText,
            Authentication authentication) {

        String username = authentication.getName();
        PostResponse response = postService.createPost(username, image, description, locationText);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }
}
