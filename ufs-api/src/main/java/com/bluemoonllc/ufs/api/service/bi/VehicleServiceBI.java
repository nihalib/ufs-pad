package com.bluemoonllc.ufs.api.service.bi;

import com.bluemoonllc.ufs.model.UfsResponse;

public interface VehicleServiceBI {

    UfsResponse getStationDetails(String fetchType, String location, String vin, Integer page, Integer pageSize);
}
