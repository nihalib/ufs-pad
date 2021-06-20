package com.bluemoonllc.ufs.api.utils;

import com.bluemoonllc.ufs.api.dao.CiamUserDao;
import com.bluemoonllc.ufs.api.dao.ProfileDao;
import com.bluemoonllc.ufs.api.dao.VehicleDao;
import com.bluemoonllc.ufs.model.CiamUser;
import com.bluemoonllc.ufs.model.Profile;
import com.bluemoonllc.ufs.model.Vehicle;

public class PojoDaoConvertor {

    public static CiamUser convertUserFromDao(CiamUserDao record) {
        return CiamUser.builder()
                .ciamId(record.getCiamId())
                .currencyCode(record.getCurrencyCode())
                .location(record.getLocation())
                .createdAt(record.getCreatedAt())
                .build();
    }

    public static CiamUserDao convertUserToDao(CiamUser ciamUser, Boolean testMode) {
        return CiamUserDao.builder()
                .ciamId(ciamUser.getCiamId())
                .currencyCode(ciamUser.getCurrencyCode())
                .location(ciamUser.getLocation())
                .isTestData(testMode)
                .build();
    }

    public static ProfileDao convertProfileToDao(Profile profile, CiamUserDao ciamUser, Boolean testMode) {
        return ProfileDao.builder()
                .ciamId(ciamUser)
                .salute(profile.getSalute())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .pinCode(profile.getPinCode())
                .timeZone(profile.getTimeZone())
                .ssn(profile.getSsn())
                .isTestData(testMode)
                .build();
    }

    public static Profile convertProfileFromDao(ProfileDao response) {
        return Profile.builder()
                .ciamId(response.getCiamId().getCiamId())
                .firstName(response.getFirstName())
                .lastName(response.getLastName())
                .salute(response.getSalute())
                .pinCode(response.getPinCode())
                .timeZone(response.getTimeZone())
                .ssn(response.getSsn())
                .createdAt(response.getCreatedAt())
                .build();
    }

    public static VehicleDao convertVehicleToDao(Vehicle vehicle, CiamUserDao ciamUserDao, Boolean testMode) {
        return VehicleDao.builder()
                .ciamId(ciamUserDao)
                .providerId(vehicle.getProviderId())
                .location(vehicle.getLocation())
                .currencyCode(vehicle.getCurrencyCode())
                .vin(vehicle.getVin())
                .chargingMode(vehicle.getChargingMode())
                .registeredAt(vehicle.getRegisteredAt())
                .timeZone(vehicle.getTimeZone())
                .isTestData(testMode)
                .build();
    }

    public static Vehicle convertVehicleFromDao(VehicleDao response) {
        return Vehicle.builder()
                .ciamId(response.getCiamId().getCiamId())
                .vin(response.getVin())
                .chargingMode(response.getChargingMode())
                .currencyCode(response.getCurrencyCode())
                .location(response.getLocation())
                .providerId(response.getProviderId())
                .registeredAt(response.getRegisteredAt())
                .timeZone(response.getTimeZone())
                .build();
    }
}
