package me.shrestho.minimalcms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    User findByEmail(String email);

}
