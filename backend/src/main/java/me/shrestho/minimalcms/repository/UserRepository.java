package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.utils.projections.UserProjection;

public interface UserRepository extends JpaRepository<User, String> {

    UserProjection findByUsername(String username);

    User findByEmail(String email);

}
