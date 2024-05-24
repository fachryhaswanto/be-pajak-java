package com.pajakbpkad.pajakbpkad.models;

import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kode_akun_pajaks")
public class KodeAkunPajak implements Serializable {
    
    @Id
    @GeneratedValue
    @JdbcTypeCode(Types.VARCHAR)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String kode;

    @Column(length = 100, nullable = false)
    private String namaAkun;

}
