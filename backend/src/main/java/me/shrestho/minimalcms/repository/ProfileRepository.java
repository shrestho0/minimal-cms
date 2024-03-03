package me.shrestho.minimalcms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.TokenBlacklisted;

public interface ProfileRepository extends JpaRepository<TokenBlacklisted, UUID> {

}
