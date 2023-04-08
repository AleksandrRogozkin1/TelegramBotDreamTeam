package org.example.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.currency.Currency;

import java.math.BigDecimal;

/*
    Уніфікований формат, до якого будуть зводитись курси валют Привата, Моно та НБУ
*/
@Setter
@Getter
@ToString
@AllArgsConstructor
public class CurrencyRateDto {
    private Currency currency;
    private BigDecimal buy;
    private BigDecimal sell;
}
