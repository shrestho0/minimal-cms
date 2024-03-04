package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import me.shrestho.minimalcms.entity.SiteFooter;
import me.shrestho.minimalcms.entity.User;

@EnableJpaRepositories
public interface SiteFooterRepository extends JpaRepository<SiteFooter, String> {

    SiteFooter findByUser(User user);

}
