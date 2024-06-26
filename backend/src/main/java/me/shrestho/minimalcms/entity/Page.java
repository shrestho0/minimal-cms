package me.shrestho.minimalcms.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.shrestho.minimalcms.utils.enums.PageStatus;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Page", indexes = {

        // user and page slug should be unique
        @Index(name = "unique_user_slug", columnList = "user, slug", unique = true)

})
@NoArgsConstructor
@AllArgsConstructor
public class Page {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PageStatus status;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "content", nullable = false, length = 3000)
    private String content; // it already has a expiry date

    @ManyToOne()
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created")
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime updated;

}
