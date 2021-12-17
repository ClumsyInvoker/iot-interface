package com.lanyun.datasource.routers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
public class RpcWasteUrlConfig {

    @Value("${lanyun.service.waste.host}")
    private String wasteHost;

    @Value("${lanyun.service.waste.port}")
    private Integer wastePort;

    private String wasteUrl;

    @PostConstruct
    public void init() {
        wasteUrl = wastePort == null ? wasteHost : wasteHost + ":" + wastePort;
    }
}
