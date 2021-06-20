package com.bluemoonllc.ufs.api.client.ctms;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class CtmsFallbackFactory implements FallbackFactory<CtmsClient> {

    @Override
    public CtmsClient create(Throwable throwable) {
        return new CtmsFallback(throwable);
    }
}
