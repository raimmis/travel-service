package com.muizarajs.travel.service.routeprice;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class DraftPriceRequest {
    @NotBlank(message = "Start terminal is mandatory")
    private String startTerminal;
    @NotBlank(message = "End terminal is mandatory")
    private String endTerminal;
    @NotEmpty (message = "Passengers are mandatory")
    private List<Passenger> passengers;

    @Data
    public static class Passenger {
        private boolean infant;
        private int luggageCount;
    }
}
