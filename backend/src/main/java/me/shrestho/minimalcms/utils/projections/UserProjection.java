package me.shrestho.minimalcms.utils.projections;

import java.time.LocalDateTime;
import me.shrestho.minimalcms.utils.enums.UserRoles;

public interface UserProjection {

    String getId();

    String getName();

    String getEmail();

    String getUsername();

    UserRoles getRole();

    LocalDateTime getCreated();

    LocalDateTime getUpdated();

}
