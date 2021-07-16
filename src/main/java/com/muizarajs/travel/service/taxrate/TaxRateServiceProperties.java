package com.muizarajs.travel.service.taxrate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties("services.tax-rate")
public class TaxRateServiceProperties {
    String url;
    Duration readTimeout;
    Duration connectionTimeout;
    String username;
    String password;
}
