package me.shrestho.minimalcms.utils.projections;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * When user searches for page
 */
public interface UserPagesProjection {

    UUID getId();

    String getTitle();

    String getSlug();

    String getStatus();

    String getContent();

    LocalDateTime getCreated();

    LocalDateTime getUpdated();

    // UserProjection getUser();
}
