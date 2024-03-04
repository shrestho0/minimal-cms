package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.SiteStyle;
import me.shrestho.minimalcms.entity.User;

public interface SiteStyleRepository extends JpaRepository<SiteStyle, String> {

    SiteStyle findByUser(User user);

}
