package com.bluemoonllc.ufs.api.client.ctms;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ctms-client", url = "${ufs.client.ctms.base-url}",
        configuration = CtmsClientConfiguration.class, fallbackFactory = CtmsFallbackFactory.class)
public interface CtmsClient extends CtmsSI {
}
