package com.kilianfogarty.FixCarolina.dto;

import com.kilianfogarty.FixCarolina.model.Status;
import java.time.Instant;

public record PostResponse(Long id,
                           String username,
                           String imagePath,
                           String description,
                           String locationText,
                           Status status,
                           Instant creationTime) {}
