package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shrestho.minimalcms.entity.SiteHeader;

public interface SiteHeaderRepository extends JpaRepository<SiteHeader, String> {

}
