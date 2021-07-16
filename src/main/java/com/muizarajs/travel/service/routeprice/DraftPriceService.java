package com.muizarajs.travel.service.routeprice;

import com.muizarajs.travel.service.baseprice.BasePriceService;
import com.muizarajs.travel.service.routeprice.DraftPriceResponse.PassengerPrice;
import com.muizarajs.travel.service.taxrate.TaxRateService;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.muizarajs.travel.service.routeprice.DraftPriceRequest.Passenger;

@Service
@RequiredArgsConstructor
public class DraftPriceService {
    private static final BigDecimal LUGGAGE_DISCOUNT_COEFFICIENT = BigDecimal.valueOf(0.3);
    private static final BigDecimal INFANT_DISCOUNT_COEFFICIENT = BigDecimal.valueOf(0.5);
    private final BasePriceService basePriceService;
    private final TaxRateService taxRateService;

    public DraftPriceResponse calculatePrice(DraftPriceRequest draftPriceRequest) {
        var basePrice = basePriceService.getBasePrice(draftPriceRequest.getStartTerminal(), draftPriceRequest.getEndTerminal());
        var taxRate = taxRateService.getDailyTaxRate(LocalDate.now());
        var routePrice = new DraftPriceResponse();

        var passengerPrices = draftPriceRequest.getPassengers().stream()
                .map(passenger ->  this.calculatePassengerPrice(passenger, basePrice, taxRate.getTaxRateDecimal()))
                .collect(Collectors.toList());

        var total = passengerPrices.stream()
                        .map(passengerPrice -> passengerPrice.getPassengerPrice().add(passengerPrice.getLuggagePrice()))
                        .reduce(Money.of(BigDecimal.ZERO, basePrice.getCurrency()), MonetaryAmount::add);

        routePrice.setPassengerPrices(passengerPrices);
        routePrice.setTotalPrice(total);
        return routePrice;
    }

    private PassengerPrice calculatePassengerPrice(Passenger passenger, MonetaryAmount basePrice, BigDecimal taxRate) {
        var ticketPriceWithoutVAT = basePrice
                .multiply(passenger.isInfant() ? INFANT_DISCOUNT_COEFFICIENT : BigDecimal.ONE);

        var luggagePriceWithoutVAT = basePrice
                .multiply(LUGGAGE_DISCOUNT_COEFFICIENT)
                .multiply(passenger.getLuggageCount());

        var ticketPrice = ticketPriceWithoutVAT.add(calculateVAT(ticketPriceWithoutVAT, taxRate));
        var luggagePrice = luggagePriceWithoutVAT.add(calculateVAT(luggagePriceWithoutVAT, taxRate));

        var passengerPrice = new PassengerPrice();
        passengerPrice.setPassengerPrice(ticketPrice);
        passengerPrice.setLuggagePrice(luggagePrice);
        return passengerPrice;
    }

    private static MonetaryAmount calculateVAT(MonetaryAmount valueWithoutVAT, BigDecimal taxRate) {
        return valueWithoutVAT.multiply(taxRate);
    }
}
