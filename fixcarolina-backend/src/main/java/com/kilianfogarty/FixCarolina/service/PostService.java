package com.kilianfogarty.FixCarolina.service;

import com.kilianfogarty.FixCarolina.dto.PostResponse;
import com.kilianfogarty.FixCarolina.model.Post;
import com.kilianfogarty.FixCarolina.model.Status;
import com.kilianfogarty.FixCarolina.model.User;
import com.kilianfogarty.FixCarolina.repository.PostRepository;
import com.kilianfogarty.FixCarolina.repository.UserRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PostService {

    // Temp until I get supabase image storage and postgres hooked up.
  private static final String UPLOAD_DIRECTORY = "src/main/resources/static/uploads/";
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final ModerationService moderationService;

  public PostService(PostRepository postRepository, UserRepository userRepository, ModerationService moderationService) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.moderationService = moderationService;
  }

  public PostResponse createPost(
      String username, MultipartFile image, String description, String locationText) {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found."));

    ModerationResult moderationResult = moderationService.moderate(image, description);

    String savedPath = saveImage(image);

    Post post = new Post(user, savedPath, description, locationText);

    post.setStatus(result.approved() ? Status.APPROVED : Status.BLOCKED);
    post.setModerationReason(moderationResult.moderationReason());

    Post saved = postRepository.save(post);

    return toResponse(saved);
  }

  public List<PostResponse> getAllPosts() {
    return postRepository.findAll().stream().map(this::toResponse).toList();
  }

  private PostResponse toResponse(Post post) {
      return new PostResponse(
              post.getId(),
              post.getUser().getUsername(),
              post.getImagePath(),
              post.getDescription(),
              post.getLocationText(),
              post.getStatus(),
              post.getCreationTime()
      );
  }

    private String saveImage(MultipartFile image) {
        try {
            Files.createDirectories(Path.of(UPLOAD_DIRECTORY));

            String extension = getExtension(image.getOriginalFilename());
            String filename = UUID.randomUUID() + extension;
            Path destination = Path.of(UPLOAD_DIRECTORY + filename);

            Files.copy(image.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image", e);
        }
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return "";
        }
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }
}
