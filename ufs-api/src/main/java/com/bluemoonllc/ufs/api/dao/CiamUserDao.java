package com.bluemoonllc.ufs.api.dao;

import liquibase.pro.packaged.C;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
@Table(name = "ciam", schema = "ufs_pad")
public class CiamUserDao implements Serializable {

    private static final long serialVersionUID = -1449775835800839903L;

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(
            name = "ciam_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "optimizer", value = "pooled-lo"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "10"),
                    @Parameter(name = "sequence_name", value = "ufs_pad.ufs_pad_ciam_sqc")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ciam_generator")
    private Long id;
    @Column(name = "ciam_id")
    private BigInteger ciamId;
    @Column(name = "location")
    private String location;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "is_test_data")
    private Boolean isTestData;
    @OneToMany(mappedBy = "ciamId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = VehicleDao.class)
    private List<VehicleDao> vehicles = new ArrayList<>();
}
