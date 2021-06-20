package com.bluemoonllc.ufs.api.client.ctms;

import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.common.PaginatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface CtmsSI {

    @GetMapping(value = "/services/v1/station/{location}")
    @ResponseBody
    ResponseEntity<UfsResponse<PaginatedResponse>> getStationByLocation(
            @RequestHeader(value = "X-Fetch-Mode", defaultValue = "") String fetchType,
            @PathVariable(name = "location") String location,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "1") Integer pageSize);
}
