package com.kilianfogarty.FixCarolina.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ModerationService {

  // Eventually I would like to train a neural network to identify NSFW photos
  private static final double MIN_DESCRIPTION_LENGTH = 10.0;
  private static final double MAX_DESCRIPTION_LENGTH = 256.0;

  public ModerationResult moderate(MultipartFile image, String description) {
    ModerationResult descriptionResult = checkDescription(description);
    if (!descriptionResult.approved()) {
      return descriptionResult;
    }

    return checkImage(image);
  }

  // Function to check description appropriate
  private ModerationResult checkDescription(String description) {
    if (description == null || description.isBlank()) {
      return ModerationResult.block("Description is empty.");
    }
    String trimmedDescription = description.trim();
    if (trimmedDescription.length() < MIN_DESCRIPTION_LENGTH) {
      return ModerationResult.block(
          "Description is too short. (Minimum " + MIN_DESCRIPTION_LENGTH + " characters.)");
    }
    if (trimmedDescription.length() > MAX_DESCRIPTION_LENGTH) {
      return ModerationResult.block(
          "Description is too long. (Maximum " + MAX_DESCRIPTION_LENGTH + " characters.)");
    }
    // Next step is a NSFW check and a gibberish check

    return ModerationResult.approve();
  }

  // Function to check image appropriate, for now it will check for bluriness.
  private ModerationResult checkImage(MultipartFile image) {

    BufferedImage bufferedImage;
    try {
      bufferedImage = ImageIO.read(image.getInputStream());
    } catch (IOException e) {
      return ModerationResult.block("Could not read image file.");
    }

    if (bufferedImage == null) {
      return ModerationResult.block("Uploaded file is not a valid image.");
    }

    return ModerationResult.approve();
  }
}
