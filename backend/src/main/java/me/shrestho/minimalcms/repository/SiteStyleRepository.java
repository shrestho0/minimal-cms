package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.SiteStyle;

public interface SiteStyleRepository extends JpaRepository<SiteStyle, String> {

}
