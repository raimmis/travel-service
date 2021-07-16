package com.muizarajs.travel.service.baseprice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.money.MonetaryAmount;

import static java.time.LocalDate.now;
import static java.time.chrono.ChronoLocalDate.from;
import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Service
@RequiredArgsConstructor
public class BasePriceService {
    private final RestTemplate basePriceServiceRestTemplate;

    public MonetaryAmount getBasePrice(String startTerminal, String endTerminal) {
        var uri = fromUri( basePriceServiceRestTemplate.getUriTemplateHandler().expand("/base-price"))
                .queryParam("startTerminal", startTerminal)
                .queryParam("endTerminal", endTerminal)
                .build()
                .encode()
                .toUri();
        return basePriceServiceRestTemplate.getForObject(uri, MonetaryAmount.class);
    }
}
