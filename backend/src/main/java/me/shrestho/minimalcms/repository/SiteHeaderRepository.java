package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import me.shrestho.minimalcms.entity.SiteHeader;
import me.shrestho.minimalcms.entity.User;

@EnableJpaRepositories
public interface SiteHeaderRepository extends JpaRepository<SiteHeader, String> {

    SiteHeader findByUser(User user);

}
