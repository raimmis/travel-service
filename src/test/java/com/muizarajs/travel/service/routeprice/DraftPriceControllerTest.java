package com.muizarajs.travel.service.routeprice;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DraftPriceControllerTest {
    @Mock
    private DraftPriceService service;

    @InjectMocks
    private DraftPriceController controller;

    @Test
    public void shouldReturnCalculatedPriceObject() {
        DraftPriceRequest request = createDraftPriceRequest();
        DraftPriceResponse response = createDraftPriceResponse();

        when(service.calculatePrice(request)).thenReturn(response);

        var result = controller.findPrice(request);

        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getTotalPrice()).isEqualTo(response.getTotalPrice());
        assertThat(result.getBody().getPassengerPrices()).isEqualTo(response.getPassengerPrices());
    }

    private DraftPriceResponse createDraftPriceResponse() {
        var response = new DraftPriceResponse();

        var passengerAdult = new DraftPriceResponse.PassengerPrice();
        passengerAdult.setPassengerPrice(Money.of(BigDecimal.valueOf(12.10), Monetary.getCurrency("EUR")));
        passengerAdult.setLuggagePrice(Money.of(BigDecimal.valueOf(7.26), Monetary.getCurrency("EUR")));

        var passengerChild = new DraftPriceResponse.PassengerPrice();
        passengerChild.setPassengerPrice(Money.of(BigDecimal.valueOf(6.05), Monetary.getCurrency("EUR")));
        passengerChild.setLuggagePrice(Money.of(BigDecimal.valueOf(3.63), Monetary.getCurrency("EUR")));

        response.setPassengerPrices(Arrays.asList(passengerAdult,passengerChild));
        response.setTotalPrice(Money.of(BigDecimal.valueOf(29.04), Monetary.getCurrency("EUR")));
        return response;
    }

    private DraftPriceRequest createDraftPriceRequest() {
        var request = new DraftPriceRequest();
        request.setStartTerminal("Riga");
        request.setEndTerminal("Vilnius");

        var passengerAdult = new DraftPriceRequest.Passenger();
        passengerAdult.setInfant(false);
        passengerAdult.setLuggageCount(2);

        var passengerChild = new DraftPriceRequest.Passenger();
        passengerChild.setInfant(true);
        passengerChild.setLuggageCount(1);

        request.setPassengers(Arrays.asList(passengerAdult,passengerChild));
        return request;
    }
}