package com.bluemoonllc.ufs.api.client.ctms;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;

public class CtmsClientConfiguration implements RequestInterceptor {

    @Bean
    @Primary
    @Scope("prototype")
    public ErrorDecoder errorDecoder() {
        return new CtmsErrorDecoder();
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer ");
    }
}
