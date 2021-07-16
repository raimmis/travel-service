package com.muizarajs.travel.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.Module;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

@Configuration
public class JacksonConfiguration {
    static {
        SpringDocUtils.getConfig().replaceWithClass(MonetaryAmount.class, CustomMonetaryAmount.class);
    }

    @Bean
    public Module moneyModule(){
        return new MoneyModule();
    }

    public static class CustomMonetaryAmount {
        @JsonProperty("amount")
        private BigDecimal amount;

        @JsonProperty("currency")
        private String currency;
    }
}
