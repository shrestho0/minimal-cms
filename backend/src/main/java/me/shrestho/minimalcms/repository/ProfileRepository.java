package me.shrestho.minimalcms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import me.shrestho.minimalcms.entity.Profile;
// import me.shrestho.minimalcms.entity.User;

@EnableJpaRepositories

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Profile findByUserId(String userId);

}
