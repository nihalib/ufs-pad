package com.bluemoonllc.ufs.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UfsResponseStatus {
    DATA_UPDATED(HttpStatus.OK, "OK", 200, "Requested data updated successfully."),
    DATA_FOUND(HttpStatus.OK, "OK", 200, "Requested data successfully returned."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "ERROR", 404, "Requested data not found."),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "ERROR",422, "Invalid record %s");

    HttpStatus httpCode;
    String description;
    Integer responseStatusCode;
    String message;
}
