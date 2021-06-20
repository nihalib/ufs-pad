package com.bluemoonllc.ufs.api.swagger.model;

import com.bluemoonllc.ufs.model.Vehicle;
import com.bluemoonllc.ufs.model.common.UfsResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVehicleSuccessResponse {
    @Schema(example = "OK", description = "Response status")
    private String status;
    @Schema(example = "200", description = "Response status code")
    private UfsResponseStatus responseCode;
    @Schema(example = "Requested data updated successfully.", description = "Response message")
    private String message;
    @Schema(example = "UFS", description = "Response system source")
    private String source;
    @Schema
    private Vehicle data;
}
