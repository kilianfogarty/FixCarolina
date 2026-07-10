package com.kilianfogarty.FixCarolina;

import java.time.Instant;

public record PostResponse(Long id,
                           String username,
                           String imagePath,
                           String description,
                           String locationText,
                           Status status,
                           Instant creationTime) {}
