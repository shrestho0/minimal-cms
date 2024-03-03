package me.shrestho.minimalcms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.TokenBlacklisted;

public interface SiteHeaderRepository extends JpaRepository<TokenBlacklisted, UUID> {

}
