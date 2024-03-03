package me.shrestho.minimalcms.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PageStatus status;

    @Column(name = "slug", nullable = false)
    private String slug;
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created")
    private LocalDateTime created;

    @LastModifiedDate
    @Column(name = "updated")
    private LocalDateTime updated;

}
