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
public class Profile {
    @Schema(example = "1234", description = "Ciam ID")
    private BigInteger ciamId;
    @Schema(example = "Mr.", description = "Salutation")
    private String salute;
    @Schema(example = "Marcus", description = "User's first name")
    private String firstName;
    @Schema(example = "Louise", description = "User's last name")
    private String lastName;
    @Schema(example = "1234", description = "User's SSN ID")
    private String ssn;
    @Schema(example = "1234", description = "User's resident pincode")
    private String pinCode;
    @Schema(example = "2021-01-01T00:00:00Z", description = "Record created timestamp")
    private LocalDateTime createdAt;
    @Schema(example = "2021-01-01T00:00:00Z", description = "Last updated timestamp")
    private LocalDateTime updatedAt;
    @Schema(example = "Asia/Shangai", description = "Time zone")
    private String timeZone;
}
