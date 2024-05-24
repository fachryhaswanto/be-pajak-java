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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "pajakGus")
public class PajakGu implements Serializable {
    
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(length = 100, nullable = false, unique = true)
    private String nomorBukti;

    @Column(length = 50, nullable = false)
    private String tanggalBukti;

    @ManyToOne
    @JoinColumn(nullable = false)
    private KodeAkunPajak kodeAkunPajak;

    @Column(nullable = false)
    private Long nilaiPajak;

    @Column(length = 100, nullable = false, unique = true)
    private String ntpn;

    @Column(length = 100, nullable = false, unique = true)
    private String billing;

    @Column(length = 50, nullable = false)
    private String tanggalSetorPajak;

    @Column(length = 50, nullable = false)
    private String npwp;

    @Column(length = 200, nullable = false)
    private String penerima;

    // private RekeningBelanja rekeningBelanja;

    @Column(length = 20, nullable = false)
    private String tahun;

    @Column(length = 200, nullable = true)
    private String buktiBayar;

    @Column(length = 20, nullable = false)
    private String validateStatus;

    @ManyToOne
    private PajakUser validatedBy;

    @Column(length = 400, nullable = true)
    private String catatanValidasi;

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
