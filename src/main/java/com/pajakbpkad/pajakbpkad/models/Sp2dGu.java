package com.pajakbpkad.pajakbpkad.models;

import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "sp2dGus")
public class Sp2dGu implements Serializable {
    
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PajakGu pajakGu;

    @Column(length = 100, nullable = false, unique = true)
    private String nomorSpm;

    @Column(length = 50, nullable = false)
    private String tanggalSpm;

    @Column(length = 100, nullable = false, unique = true)
    private String nomorSp2d;

    @Column(length = 100, nullable = false)
    private String tanggalSp2d;

    @Column(nullable = false)
    private Long nilaiSp2d;

    @Column(length = 500, nullable = false)
    private String keterangan;

    @Column(length = 50, nullable = false)
    private String tahun;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PajakUser createdBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private PajakUser updatedBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

}
