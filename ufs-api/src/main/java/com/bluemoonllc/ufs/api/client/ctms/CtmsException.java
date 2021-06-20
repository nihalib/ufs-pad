package com.bluemoonllc.ufs.api.client.ctms;

import com.bluemoonllc.ufs.model.UfsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CtmsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "Status %s ; response: %s";

    private final HttpStatus status;
    private final UfsResponse response;

    @Override
    public String getMessage() {
        return String.format(MESSAGE_TEMPLATE, status, response);
    }
}
