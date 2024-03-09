package me.shrestho.minimalcms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.utils.enums.UserRoles;
import me.shrestho.minimalcms.utils.projections.UserProjection;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<UserProjection> findTop5ByOrderByCreatedDesc();

    @Query(value = "SELECT count(u.id) FROM user u WHERE DATE(u.created) = CURDATE()", nativeQuery = true)
    long countByCreatedToday();

    @Query(value = "SELECT count(u.id) FROM user u WHERE (u.username LIKE %?1% OR u.email LIKE %?1%) AND role = 'USER'", nativeQuery = true)
    long countRegularUserByQueryAndLimit(String userQuery, int limit, int offset);

    @Query(value = "SELECT count(u.id) FROM user u WHERE (u.username LIKE %?1% OR u.email LIKE %?1%) AND role = 'ADMIN'", nativeQuery = true)
    long countAdminUserByQueryAndLimit(String userQuery, int limit, int offset);

    @Query(value = "SELECT * FROM user u WHERE (u.username LIKE %?1% OR u.email LIKE %?1%) AND role = 'USER' LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<UserProjection> findRegularUserByQueryAndLimit(String userQuery, int limit, int offset);

    @Query(value = "SELECT * FROM user u WHERE (u.username LIKE %?1% OR u.email LIKE %?1%) AND role = 'ADMIN' LIMIT ?2 OFFSET ?3", nativeQuery = true)
    List<UserProjection> findAdminUserByQueryAndLimit(String userQuery, int limit, int offset);

    List<User> findByNameLikeOrUsernameLikeAndRoleEquals(String userQuery, String userQuery2, UserRoles role);

    List<User> findByNameLikeOrUsernameLikeOrIdEqualsAndRoleEquals(String likeQuery, String likeQuery2,
            String likeQuery3, UserRoles role);

}
