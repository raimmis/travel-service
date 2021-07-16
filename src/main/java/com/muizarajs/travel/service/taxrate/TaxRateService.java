package com.muizarajs.travel.service.taxrate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Service
@RequiredArgsConstructor
public class TaxRateService {
    private final RestTemplate taxRateServiceRestTemplate;

    public TaxRate getDailyTaxRate(LocalDate date) {
        var uri = fromUri( taxRateServiceRestTemplate.getUriTemplateHandler().expand("/tax-rate"))
                .queryParam("date", date)
                .build()
                .encode()
                .toUri();
        return taxRateServiceRestTemplate.getForObject(uri, TaxRate.class);
    }
}
