package com.muizarajs.travel.service.baseprice;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BasePriceServiceConfiguration {
    private final BasePriceServiceProperties basePriceServiceProperties;


    @Bean
    public RestTemplate basePriceServiceRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(basePriceServiceProperties.getUrl())
                .setConnectTimeout(basePriceServiceProperties.getConnectionTimeout())
                .setReadTimeout(basePriceServiceProperties.getReadTimeout())
                .basicAuthentication(basePriceServiceProperties.getUsername(), basePriceServiceProperties.getPassword())
                .build();
    }
}
