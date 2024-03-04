package me.shrestho.minimalcms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.utils.projections.UserProjection;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<UserProjection> findTop5ByOrderByCreatedDesc();

    @Query(value = "SELECT count(u.id) FROM user u WHERE DATE(u.created) = CURDATE()", nativeQuery = true)
    long countByCreatedToday();

}
