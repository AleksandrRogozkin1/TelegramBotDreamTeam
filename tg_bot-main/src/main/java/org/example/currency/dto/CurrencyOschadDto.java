package org.example.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.currency.Currency;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CurrencyOschadDto {
    private Currency currency;
    private BigDecimal buy;
    private BigDecimal sell;
}
