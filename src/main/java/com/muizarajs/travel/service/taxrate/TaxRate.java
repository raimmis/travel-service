package com.muizarajs.travel.service.taxrate;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TaxRate {
    private BigDecimal taxRateDecimal;
    private BigDecimal taxRatePercentage;
    private LocalDate date;
}
