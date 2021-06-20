package com.bluemoonllc.ufs.api.dao;

import com.bluemoonllc.ufs.model.common.ChargingModes;
import com.bluemoonllc.ufs.model.common.ProviderEnum;
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
@Table(name = "vehicle", schema = "ufs_pad")
public class VehicleDao implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GenericGenerator(
            name = "vehicle_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "optimizer", value = "pooled-lo"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "10"),
                    @Parameter(name = "sequence_name", value = "ufs_pad.ufs_pad_vehicle_sqc")
            })
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_generator")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ciam_id", referencedColumnName = "id")
    private CiamUserDao ciamId;
    @Column(name = "provider_id", nullable = false)
    private ProviderEnum providerId;
    @Column(name = "location")
    private String location;
    @Column(name = "currency_code")
    private String currencyCode;
    @Column(name = "vin")
    private String vin;
    @Column(name = "charging_mode")
    private ChargingModes chargingMode;
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;
    @Column(name = "timezone")
    private String timeZone;
    @Column(name = "is_test_data")
    private Boolean isTestData;
}
