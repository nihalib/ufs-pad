package com.bluemoonllc.ufs.api.dao;

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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
@Table(name = "profile", schema = "ufs_pad")
public class ProfileDao implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(
            name = "profile_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "optimizer", value = "pooled-lo"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "10"),
                    @Parameter(name = "sequence_name", value = "ufs_pad.ufs_pad_profile_sqc")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_generator")
    private Long id;
    @OneToOne
    @JoinColumn(name = "ciam_id", referencedColumnName = "id")
    private CiamUserDao ciamId;
    @Column(name = "salute")
    private String salute;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "ssn")
    private String ssn;
    @Column(name = "pincode")
    private String pinCode;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "timezone")
    private String timeZone;
    @Column(name = "is_test_data")
    private Boolean isTestData;
}
