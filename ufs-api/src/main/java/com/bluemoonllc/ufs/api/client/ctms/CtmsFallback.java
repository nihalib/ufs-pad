package com.bluemoonllc.ufs.api.client.ctms;

import com.bluemoonllc.ctms.model.Station;
import com.bluemoonllc.ufs.api.client.FallbackLogCreator;
import com.bluemoonllc.ufs.model.UfsResponse;
import com.bluemoonllc.ufs.model.common.PaginatedResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class CtmsFallback implements CtmsClient {
    private static final String CLIENT_NAME = "CtmsClient";

    private final Throwable cause;

    public CtmsFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public ResponseEntity<UfsResponse<PaginatedResponse>> getStationByLocation(String fetchType, String location,
                                                                        Integer page, Integer pageSize) {
        return mapException(getLogTemplate("getStationByLocation")
                .addParameter("X-Fetch-Mode", fetchType)
                .addParameter("location", location)
                .addParameter("page", page)
                .addParameter("pageSize", pageSize));
    }

    private <T> ResponseEntity<UfsResponse<T>> mapException(FallbackLogCreator logCreator) {
        log.error(logCreator.buildLogEntry());
        if (cause instanceof CtmsException) {
            CtmsException exception = (CtmsException) cause;
            return ResponseEntity.status(exception.getStatus()).body(exception.getResponse());
        } else if (cause instanceof FeignException) {
            return ResponseEntity.status(((FeignException) cause).status()).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    private FallbackLogCreator getLogTemplate(String methodName) {
        return new FallbackLogCreator().setClassName(CLIENT_NAME).setError(cause).setMethodName(methodName);
    }
}
