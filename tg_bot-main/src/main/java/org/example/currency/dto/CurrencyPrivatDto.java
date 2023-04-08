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
public class CurrencyPrivatDto {
    private Currency ccy;
    private Currency base_ccy;
    private BigDecimal buy;
    private BigDecimal sale;
}
