package com.bluemoonllc.ufs.api.service.bi;

import com.bluemoonllc.ufs.model.CiamUser;
import com.bluemoonllc.ufs.model.Profile;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.Vehicle;

import java.math.BigInteger;

public interface RegisterBI {

    UfsResponse addCiam(CiamUser ciamUser, Boolean testMode);

    UfsResponse getCiam(String fetchType, Integer page, Integer pageSize);

    UfsResponse addProfile(Profile profile, Boolean testMode);

    UfsResponse getProfile(String fetchType, Integer page, Integer pageSize);

    UfsResponse getProfileByCiam(String fetchType, BigInteger ciamId);

    UfsResponse addVehicle(Vehicle vehicle, Boolean testMode);

    UfsResponse getVehicle(String fetchType, BigInteger ciamId, Integer page, Integer pageSize);
}
