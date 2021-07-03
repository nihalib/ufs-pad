package com.bluemoonllc.ufs.api.service;

import com.bluemoonllc.ufs.api.client.ctms.CtmsClient;
import com.bluemoonllc.ufs.api.dao.VehicleDao;
import com.bluemoonllc.ufs.api.repository.VehicleRegistrationRepository;
import com.bluemoonllc.ufs.api.service.bi.VehicleServiceBI;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.common.PaginatedResponse;
import com.bluemoonllc.ufs.model.common.UfsResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class VehicleService implements VehicleServiceBI {

    private final VehicleRegistrationRepository vehicleRegistrationRepo;
    private final CtmsClient ctmsClient;

    public VehicleService(VehicleRegistrationRepository vehicleRegistrationRepo, CtmsClient ctmsClient) {
        this.vehicleRegistrationRepo = vehicleRegistrationRepo;
        this.ctmsClient = ctmsClient;
    }

    @Override
    public UfsResponse getStationDetails(String fetchType, String location, String vin, Integer page, Integer pageSize) {
        log.info("Get Station details request fetch type {}, page {}, pageSize {}", fetchType, page, pageSize);
        Optional<VehicleDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = vehicleRegistrationRepo.findByVin(vin);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = vehicleRegistrationRepo.findByVinAndAndIsTestData(vin,true);
        } else {
            result = vehicleRegistrationRepo.findByVinAndAndIsTestData(vin,false);
        }

        if (!result.isPresent()) {
            log.info("Requested vehicle not found");
            UfsResponseStatus status = UfsResponseStatus.NOT_FOUND;
            return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", null);
        }
        ResponseEntity<UfsResponse<PaginatedResponse>> responseEntity = ctmsClient.getStationByLocation(
                fetchType, location, page, pageSize);
        if (!responseEntity.getStatusCode().is2xxSuccessful() || isNull(responseEntity.getBody())) {
            log.info("Requested location not found");
            UfsResponseStatus status = UfsResponseStatus.NOT_FOUND;
            return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", null);
        }

        log.info("Requested location details returned");
        PaginatedResponse response = responseEntity.getBody().getData();
        UfsResponseStatus status = UfsResponseStatus.DATA_FOUND;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", response);
    }
}
