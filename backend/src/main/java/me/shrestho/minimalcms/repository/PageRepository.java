package me.shrestho.minimalcms.repository;

import java.util.List;

// import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import me.shrestho.minimalcms.entity.Page;
import me.shrestho.minimalcms.entity.User;
import me.shrestho.minimalcms.utils.projections.UserPagesProjection;

@EnableJpaRepositories
public interface PageRepository extends JpaRepository<Page, String> {

    List<UserPagesProjection> findAllByUser(User user);

    Page findByUserAndSlug(User user, String slug);

    Page findByUserAndId(User user, String string);

    @Query(value = "SELECT count(p.id) FROM page p WHERE p.user = ?1 AND (p.title LIKE %?2% OR p.content LIKE %?2% OR p.slug LIKE %?2%  )", nativeQuery = true)
    Integer countByFilter(String userId, String q);

    // no order by
    @Query(value = "SELECT p.* FROM page p WHERE p.user = ?1 AND (p.title LIKE %?2% OR p.content LIKE %?2% OR p.slug LIKE %?2% ) LIMIT ?3, ?4", nativeQuery = true)
    List<UserPagesProjection> findByFilter(String userId, String q, int offset, int limit);

    @Query(value = "SELECT count(p.id) FROM page p WHERE p.user = ?1 AND (p.title LIKE %?2% OR p.content LIKE %?2% OR p.slug LIKE %?2%  ) AND p.status = ?3", nativeQuery = true)
    Integer countByFilterAndStatus(String userId, String q, String status);

    // no order by
    @Query(value = "SELECT p.* FROM page p WHERE p.user = ?1 AND (p.title LIKE %?2% OR p.content LIKE %?2% OR p.slug LIKE %?2% ) AND p.status = ?3 LIMIT ?4, ?5", nativeQuery = true)
    List<UserPagesProjection> findByFilterAndStatus(String userId, String q, String status, int offset, int limit);

    @Query(value = "SELECT p.* FROM page p WHERE p.user = ?1 AND (p.title LIKE %?2% OR p.slug LIKE %?2% )", nativeQuery = true)
    List<UserPagesProjection> searchByUserId(String userId, String q);

    List<UserPagesProjection> findTop5ByOrderByCreatedDesc();

    @Query(value = "SELECT count(p.id) FROM page p WHERE p.user = ?1", nativeQuery = true)
    long countByUserId(String id);

    @Query(value = "SELECT count(p.id) FROM page p WHERE p.user = ?1 AND p.status = ?2", nativeQuery = true)
    long countByUserIdAndStatus(String id, String status);

    List<UserPagesProjection> findTop5ByUserOrderByUpdatedDesc(User user);

    @Query(value = "SELECT count(p.id) FROM page p WHERE DATE(p.created) = CURDATE()", nativeQuery = true)
    long countByCreatedToday();

    void deleteAllByUser(User user);

}
