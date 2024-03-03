package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.TokenBlacklisted;

public interface TokenBlacklistedRepository extends JpaRepository<TokenBlacklisted, String> {

    TokenBlacklisted findByRefreshToken(String refreshToken);

}
