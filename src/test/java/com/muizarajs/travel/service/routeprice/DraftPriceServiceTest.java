package com.muizarajs.travel.service.routeprice;

import com.muizarajs.travel.service.baseprice.BasePriceService;
import com.muizarajs.travel.service.taxrate.TaxRate;
import com.muizarajs.travel.service.taxrate.TaxRateService;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DraftPriceServiceTest {
    @Mock
    private TaxRateService taxRateService;

    @Mock
    private BasePriceService basePriceService;

    @InjectMocks
    private DraftPriceService service;

    @Test
    public void shouldReturnCalculatedPriceObject() {
        var request = createDraftPriceRequest();
        var response = createDraftPriceResponse();

        var taxRate = new TaxRate();
        taxRate.setTaxRateDecimal(BigDecimal.valueOf(0.21));
        taxRate.setDate(LocalDate.now());

        when(taxRateService.getDailyTaxRate(LocalDate.now())).thenReturn(taxRate);
        when(basePriceService.getBasePrice("Riga", "Vilnius")).thenReturn(Money.of(10, Monetary.getCurrency("EUR")));

        var result = service.calculatePrice(request);

        assertThat(result).isNotNull();
        assertThat(result.getTotalPrice()).isEqualTo(response.getTotalPrice());
        assertThat(result.getPassengerPrices()).isEqualTo(response.getPassengerPrices());
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