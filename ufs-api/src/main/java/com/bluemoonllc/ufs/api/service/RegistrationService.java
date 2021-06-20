package com.bluemoonllc.ufs.api.service;

import com.bluemoonllc.ufs.api.dao.CiamUserDao;
import com.bluemoonllc.ufs.api.dao.ProfileDao;
import com.bluemoonllc.ufs.api.dao.VehicleDao;
import com.bluemoonllc.ufs.api.repository.ProfileRegistrationRepository;
import com.bluemoonllc.ufs.api.repository.UserRegistrationRepository;
import com.bluemoonllc.ufs.api.repository.VehicleRegistrationRepository;
import com.bluemoonllc.ufs.api.service.bi.RegisterBI;
import com.bluemoonllc.ufs.api.utils.PojoDaoConvertor;
import com.bluemoonllc.ufs.model.CiamUser;
import com.bluemoonllc.ufs.model.Profile;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.Vehicle;
import com.bluemoonllc.ufs.model.common.PaginatedResponse;
import com.bluemoonllc.ufs.model.common.UfsResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bluemoonllc.ufs.api.utils.PojoDaoConvertor.convertProfileFromDao;
import static com.bluemoonllc.ufs.api.utils.PojoDaoConvertor.convertProfileToDao;
import static com.bluemoonllc.ufs.api.utils.PojoDaoConvertor.convertUserFromDao;
import static com.bluemoonllc.ufs.api.utils.PojoDaoConvertor.convertUserToDao;
import static com.bluemoonllc.ufs.api.utils.PojoDaoConvertor.convertVehicleFromDao;
import static com.bluemoonllc.ufs.api.utils.PojoDaoConvertor.convertVehicleToDao;

@Slf4j
@Service
public class RegistrationService implements RegisterBI {

    private final UserRegistrationRepository userRegistrationRepo;
    private final ProfileRegistrationRepository profileRegistrationRepo;
    private final VehicleRegistrationRepository vehicleRegistrationRepo;

    public RegistrationService(UserRegistrationRepository userRegistrationRepo,
                               ProfileRegistrationRepository profileRegistrationRepo,
                               VehicleRegistrationRepository vehicleRegistrationRepo) {
        this.userRegistrationRepo = userRegistrationRepo;
        this.profileRegistrationRepo = profileRegistrationRepo;
        this.vehicleRegistrationRepo = vehicleRegistrationRepo;
    }

    @Override
    public UfsResponse addCiam(CiamUser ciamUser, Boolean testMode) {
        CiamUserDao ciam = convertUserToDao(ciamUser, testMode);
        ciam = ciam.toBuilder().createdAt(LocalDateTime.now()).build();

        CiamUserDao response = userRegistrationRepo.save(ciam);
        CiamUser result = convertUserFromDao(response);

        UfsResponseStatus status = UfsResponseStatus.DATA_UPDATED;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", result);
    }

    @Override
    public UfsResponse getCiam(String fetchType, Integer page, Integer pageSize) {
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<CiamUserDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = userRegistrationRepo.findAll(pageable);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = userRegistrationRepo.findCiamUserDaoByIsTestData(true, pageable);
        } else {
            result = userRegistrationRepo.findCiamUserDaoByIsTestData(false, pageable);
        }

        List<CiamUserDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            UfsResponseStatus status = UfsResponseStatus.NOT_FOUND;
            return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", null);
        }

        List<CiamUser> ciamUsers = resultContent.stream()
                .map(PojoDaoConvertor::convertUserFromDao)
                .collect(Collectors.toList());

        response.setData(ciamUsers);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        UfsResponseStatus status = UfsResponseStatus.DATA_FOUND;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", response);
    }

    @Override
    public UfsResponse addProfile(Profile profile, Boolean testMode) {
        Optional<CiamUserDao> ciamUser = userRegistrationRepo.findByCiamId(profile.getCiamId());

        if (!ciamUser.isPresent()) {
            UfsResponseStatus status = UfsResponseStatus.UNPROCESSABLE_ENTITY;
            String message = String.format(status.getMessage(), profile.getCiamId());
            return new UfsResponse<>(status.getDescription(), status, message, "UFS", null);
        }

        ProfileDao profileDao = convertProfileToDao(profile, ciamUser.get(), testMode);
        profileDao = profileDao.toBuilder().createdAt(LocalDateTime.now()).build();

        ProfileDao response = profileRegistrationRepo.save(profileDao);
        Profile result = convertProfileFromDao(response);

        UfsResponseStatus status = UfsResponseStatus.DATA_UPDATED;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", result);
    }

    @Override
    public UfsResponse getProfile(String fetchType, Integer page, Integer pageSize) {
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProfileDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = profileRegistrationRepo.findAll(pageable);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = profileRegistrationRepo.findProfileDaoByIsTestData(true, pageable);
        } else {
            result = profileRegistrationRepo.findProfileDaoByIsTestData(false, pageable);
        }

        List<ProfileDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            UfsResponseStatus status = UfsResponseStatus.NOT_FOUND;
            return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", null);
        }

        List<Profile> profiles = resultContent.stream()
                .map(PojoDaoConvertor::convertProfileFromDao)
                .collect(Collectors.toList());

        response.setData(profiles);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        UfsResponseStatus status = UfsResponseStatus.DATA_FOUND;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", response);
    }

    @Override
    public UfsResponse getProfileByCiam(String fetchType, BigInteger ciamId) {
        Optional<ProfileDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = profileRegistrationRepo.findAllByCiamId(ciamId);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = profileRegistrationRepo.findProfileDaoByCiamIdAndIsTestData(ciamId,true);
        } else {
            result = profileRegistrationRepo.findProfileDaoByCiamIdAndIsTestData(ciamId,false);
        }

        if (!result.isPresent()) {
            UfsResponseStatus status = UfsResponseStatus.NOT_FOUND;
            return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", null);
        }

        Profile response = PojoDaoConvertor.convertProfileFromDao(result.get());
        UfsResponseStatus status = UfsResponseStatus.DATA_FOUND;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", response);
    }

    @Override
    public UfsResponse addVehicle(Vehicle vehicle, Boolean testMode) {
        Optional<CiamUserDao> ciamUser = userRegistrationRepo.findByCiamId(vehicle.getCiamId());

        if (!ciamUser.isPresent()) {
            UfsResponseStatus status = UfsResponseStatus.UNPROCESSABLE_ENTITY;
            String message = String.format(status.getMessage(), vehicle.getCiamId());
            return new UfsResponse<>(status.getDescription(), status, message, "UFS", null);
        }

        VehicleDao vehicleDao = convertVehicleToDao(vehicle, ciamUser.get(), testMode);

        VehicleDao response = vehicleRegistrationRepo.save(vehicleDao);
        Vehicle result = convertVehicleFromDao(response);

        UfsResponseStatus status = UfsResponseStatus.DATA_UPDATED;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", result);
    }

    @Override
    public UfsResponse getVehicle(String fetchType, BigInteger ciamId, Integer page, Integer pageSize) {
        PaginatedResponse response = new PaginatedResponse();

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<VehicleDao> result;
        if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("ALL")) {
            result = vehicleRegistrationRepo.findVehicleDaoByCiamId(ciamId, pageable);
        } else if (!fetchType.isEmpty() && fetchType.equalsIgnoreCase("TEST")) {
            result = vehicleRegistrationRepo.findVehicleDaoByIsTestData(ciamId, true, pageable);
        } else {
            result = vehicleRegistrationRepo.findVehicleDaoByIsTestData(ciamId,false, pageable);
        }

        List<VehicleDao> resultContent = result.getContent();
        if (resultContent.isEmpty()) {
            UfsResponseStatus status = UfsResponseStatus.NOT_FOUND;
            return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", null);
        }

        List<Vehicle> ciamUsers = resultContent.stream()
                .map(PojoDaoConvertor::convertVehicleFromDao)
                .collect(Collectors.toList());

        response.setData(ciamUsers);
        response.setPageSize(result.getSize());
        response.setTotalPage(result.getTotalPages());
        response.setTotalRecords((int) result.getTotalElements());

        UfsResponseStatus status = UfsResponseStatus.DATA_FOUND;
        return new UfsResponse<>(status.getDescription(), status, status.getMessage(), "UFS", response);
    }
}
