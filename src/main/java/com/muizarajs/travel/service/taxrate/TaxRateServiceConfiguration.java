package com.muizarajs.travel.service.taxrate;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class TaxRateServiceConfiguration {
    private final TaxRateServiceProperties taxRateServiceProperties;


    @Bean
    public RestTemplate taxRateServiceRestTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(taxRateServiceProperties.getUrl())
                .setConnectTimeout(taxRateServiceProperties.getConnectionTimeout())
                .setReadTimeout(taxRateServiceProperties.getReadTimeout())
                .build();
    }
}
