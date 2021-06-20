package com.bluemoonllc.ufs.api.controller;

import com.bluemoonllc.ufs.api.service.bi.RegisterBI;
import com.bluemoonllc.ufs.api.swagger.model.DataNotFoundResponse;
import com.bluemoonllc.ufs.api.swagger.model.PaginatedProfileSuccessResponse;
import com.bluemoonllc.ufs.api.swagger.model.PaginatedUserRegisterSuccessResponse;
import com.bluemoonllc.ufs.api.swagger.model.PaginatedVehicleSuccessResponse;
import com.bluemoonllc.ufs.api.swagger.model.UnProcessableReponse;
import com.bluemoonllc.ufs.api.swagger.model.UpdateProfileSuccessResponse;
import com.bluemoonllc.ufs.api.swagger.model.UpdateUserRegistrationSuccessResponse;
import com.bluemoonllc.ufs.api.swagger.model.UpdateVehicleSuccessResponse;
import com.bluemoonllc.ufs.model.CiamUser;
import com.bluemoonllc.ufs.model.Profile;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.Vehicle;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(value = "/registration/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

    private final RegisterBI service;

    public RegistrationController(RegisterBI service) {
        this.service = service;
    }

    @Operation(operationId = "postUsers", tags = "User", summary = "Register user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration is successful",
                    content = @Content(schema = @Schema(implementation = UpdateUserRegistrationSuccessResponse.class)))
    })
    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UfsResponse> registerCiam(@Validated @RequestBody CiamUser ciamUser,
                                                    @RequestHeader(value = "X-Test-Mode", defaultValue = "false")
                                                            Boolean testMode) {
        UfsResponse response = service.addCiam(ciamUser, testMode);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "getUsers", tags = "User",
            summary = "Provides list of available registered user details in paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register user list successfully provided",
                    content = @Content(schema = @Schema(implementation = PaginatedUserRegisterSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "/users")
    public ResponseEntity<UfsResponse> getCiams(@RequestHeader(value = "X-Fetch-Mode", required = false,
                                                defaultValue = "") String fetchType,
                                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "pageSize", defaultValue = "1")
                                                        Integer pageSize) {
        UfsResponse response = service.getCiam(fetchType, page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "postProfile", tags = "Profiles", summary = "Add Profile details for registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile details successfully updated",
                    content = @Content(schema = @Schema(implementation = UpdateProfileSuccessResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable data",
                    content = @Content(schema = @Schema(implementation = UnProcessableReponse.class)))
    })
    @PostMapping(value = "/profiles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UfsResponse> addProfile(@Validated @RequestBody Profile profile,
                                                  @RequestHeader(value = "X-Test-Mode", defaultValue = "false")
                                                          Boolean testMode) {
        UfsResponse response = service.addProfile(profile, testMode);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "getProfiles", tags = "Profiles",
            summary = "Provides list of available registered user profiles in paginated format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register profile list successfully provided",
                    content = @Content(schema = @Schema(implementation = PaginatedProfileSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "/profiles")
    public ResponseEntity<UfsResponse> getProfile(@RequestHeader(value = "X-Fetch-Mode", required = false,
                                                  defaultValue = "") String fetchType,
                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "pageSize", defaultValue = "1")
                                                          Integer pageSize) {
        UfsResponse response = service.getProfile(fetchType, page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "getProfileByCiamId", tags = "Profiles", summary = "Get Profile details for registered ciamId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile details successfully provided",
                    content = @Content(schema = @Schema(implementation = UpdateProfileSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "/profiles/{ciamId}")
    public ResponseEntity<UfsResponse> getProfileByCiam(@RequestHeader(value = "X-Fetch-Mode", required = false,
                                                        defaultValue = "") String fetchType,
                                                        @PathVariable(name = "ciamId") BigInteger ciamId) {
        UfsResponse response = service.getProfileByCiam(fetchType, ciamId);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "postVehicle", tags = "Vehicle", summary = "Add Vehicle details for registered user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle details successfully updated",
                    content = @Content(schema = @Schema(implementation = UpdateVehicleSuccessResponse.class))),
            @ApiResponse(responseCode = "422", description = "Unprocessable data",
                    content = @Content(schema = @Schema(implementation = UnProcessableReponse.class)))
    })
    @PostMapping(value = "/vehicles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UfsResponse> addVehicle(@Validated @RequestBody Vehicle vehicle,
                                                  @RequestHeader(value = "X-Test-Mode", defaultValue = "false")
                                                          Boolean testMode) {
        UfsResponse response = service.addVehicle(vehicle, testMode);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }

    @Operation(operationId = "getVehicleByCiamId", tags = "Vehicle", summary = "Get Vehicle details for registered ciamId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehcile details successfully provided",
                    content = @Content(schema = @Schema(implementation = PaginatedVehicleSuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Information not found",
                    content = @Content(schema = @Schema(implementation = DataNotFoundResponse.class)))
    })
    @GetMapping(value = "/vehicles/{ciamId}")
    public ResponseEntity<UfsResponse> getVehicle(@RequestHeader(value = "X-Fetch-Mode", required = false,
                                                  defaultValue = "") String fetchType,
                                                  @PathVariable(name = "ciamId", required = true) BigInteger ciamId,
                                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                  @RequestParam(name = "pageSize", defaultValue = "1")
                                                          Integer pageSize) {
        UfsResponse response = service.getVehicle(fetchType, ciamId, page, pageSize);
        return new ResponseEntity<>(response, response.getResponseCode().getHttpCode());
    }
}
