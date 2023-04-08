package org.example.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.currency.Currency;

import java.math.BigDecimal;

/*
    Клас, для парсингу з json відповіді банка в об'єкт
*/
@Setter
@Getter
@ToString
@AllArgsConstructor
public class CurrencyMonoDto {
    private Currency currencyCodeA;
    private Currency currencyCodeB;
    private long date;
    private BigDecimal rateBuy;
    private BigDecimal rateCross;
    private BigDecimal rateSell;
}
