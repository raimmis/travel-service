package com.muizarajs.travel.service.baseprice;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties("services.base-price")
public class BasePriceServiceProperties {
    String url;
    Duration readTimeout;
    Duration connectionTimeout;
    String username;
    String password;
}
