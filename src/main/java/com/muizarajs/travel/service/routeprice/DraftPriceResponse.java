package com.muizarajs.travel.service.routeprice;

import lombok.Data;

import javax.money.MonetaryAmount;
import java.util.List;

@Data
public class DraftPriceResponse {
    private MonetaryAmount totalPrice;
    private List<PassengerPrice> passengerPrices;

    @Data
    public static class PassengerPrice {
        private MonetaryAmount passengerPrice;
        private MonetaryAmount luggagePrice;
    }
}
