package me.shrestho.minimalcms.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "BlackListedToken", indexes = {
        @Index(name = "refreshToken", columnList = "refreshToken", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor

public class TokenBlacklisted {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "refreshToken", unique = true, nullable = false, length = 1000)
    private String refreshToken; // it already has a expiry date

}
