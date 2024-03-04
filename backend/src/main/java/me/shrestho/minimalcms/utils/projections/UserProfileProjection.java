package me.shrestho.minimalcms.utils.projections;

import java.time.LocalDateTime;

public interface UserProfileProjection {

    String getId();

    String getName();

    String getEmail();

    String getUsername();

    String getUserId();

    LocalDateTime getCreated();

    LocalDateTime getUpdated();

}
