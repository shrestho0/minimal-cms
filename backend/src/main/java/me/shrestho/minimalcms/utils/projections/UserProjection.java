package me.shrestho.minimalcms.utils.projections;

import java.time.LocalDateTime;
import java.util.UUID;

import me.shrestho.minimalcms.utils.enums.UserRoles;

public interface UserProjection {

    UUID getId();

    String getName();

    String getEmail();

    String getUsername();

    UserRoles getRole();

    LocalDateTime getCreated();

    LocalDateTime getUpdated();

}
