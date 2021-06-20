package com.bluemoonllc.ufs.model;

import com.bluemoonllc.ufs.model.common.UfsResponseStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UfsResponse<T> {
    @Schema(example = "OK", description = "Response status")
    private String status;
    @Schema(example = "200", description = "Response status code")
    private UfsResponseStatus responseCode;
    @Schema(example = "Requested data successfully returned.", description = "Response message")
    private String message;
    @Schema(example = "UFS", description = "Response system source")
    private String source;
    @Schema
    private T data;
}
