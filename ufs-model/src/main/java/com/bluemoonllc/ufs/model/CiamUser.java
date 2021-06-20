package com.bluemoonllc.ufs.model;

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
public class CiamUser {
    @Schema(example = "1234", description = "Ciam ID")
    private BigInteger ciamId;
    @Schema(example = "Paris", description = "User location")
    private String location;
    @Schema(example = "EUR", description = "Currency code")
    private String currencyCode;
    @Schema(example = "2021-01-01T00:00:00Z", description = "Record created timestamp")
    private LocalDateTime createdAt;
    @Schema(example = "2021-01-01T00:00:00Z", description = "Last updated timestamp")
    private LocalDateTime updatedAt;
}
