package com.bluemoonllc.ufs.api.client.ctms;

import com.bluemoonllc.ufs.model.UfsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Slf4j
public class CtmsErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder delegate = new Default();

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {
        if (HttpStatus.valueOf(response.status()).is4xxClientError()) {
            try {
                if (nonNull(response.body())) {
                    UfsResponse<?> responseBody = response.body().length() > 0 ?
                            mapper.readValue(response.body().asInputStream(), UfsResponse.class) :
                            new UfsResponse<>();
                    return new CtmsException(HttpStatus.valueOf(response.status()), responseBody);
                }
            } catch (IOException ex) {
                log.error("Couldn't parse the body of reponse ", ex);
            }
        }
        return this.delegate.decode(s, response);
    }
}
