package com.bluemoonllc.ufs.model;

import com.bluemoonllc.ufs.model.common.ChargingModes;
import com.bluemoonllc.ufs.model.common.ProviderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Vehicle {
    @Schema(example = "1234", description = "Ciam ID")
    private BigInteger ciamId;
    @Schema(example = "DCS", description = "Charging provider id", allowableValues = "DCS, CPI, NAV")
    private ProviderEnum providerId;
    @Schema(example = "Paris", description = "Registered vehicle's location")
    private String location;
    @Schema(example = "EUR", description = "Currency code")
    private String currencyCode;
    @Schema(example = "WDD12345090", description = "Vehicle identifier")
    private String vin;
    @Schema(example = "AC3", description = "Charging mode", allowableValues = "AC1, AC3, DC")
    private ChargingModes chargingMode;
    @Schema(example = "2021-01-01T00:00:00Z", description = "Vehicle registered timestamp")
    private LocalDateTime registeredAt;
    @Schema(example = "Asia/Shangai", description = "Time zone")
    private String timeZone;
}
