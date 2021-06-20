package com.bluemoonllc.ufs.api.controller;

import com.bluemoonllc.ufs.api.service.bi.VehicleServiceBI;
import com.bluemoonllc.ufs.api.swagger.model.DataNotFoundResponse;
import com.bluemoonllc.ufs.api.swagger.model.PaginatedUserRegisterSuccessResponse;
import com.bluemoonllc.ufs.model.UfsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/service/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleServiceController {

    private final VehicleServiceBI vehicleService;

    public VehicleServiceController(VehicleServiceBI vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(operationId = "getChargingStation", tags = "Charging-Station",
            summary = "Provides list of available registered user details in paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register user list successfully provided",
                    content = @Content(schema = @Schema(implementation = PaginatedUserRegisterSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "charging-station/{location}/vehicle/{vin}")
    public ResponseEntity<UfsResponse> getCiams(@RequestHeader(value = "X-Fetch-Mode", required = false,
                                                defaultValue = "") String fetchType,
                                                @PathVariable(value = "location") String location,
                                                @PathVariable(value = "vin") String vin,
                                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "pageSize", defaultValue = "1")
                                                        Integer pageSize) {
        UfsResponse response = vehicleService.getStationDetails(fetchType, location, vin, page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }
}
