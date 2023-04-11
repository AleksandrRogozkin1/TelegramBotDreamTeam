package org.example.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.currency.Bank;
import org.example.currency.Currency;

import java.math.BigDecimal;
import java.util.Calendar;

/*
    Уніфікований формат, до якого будуть зводитись курси валют Привата, Моно та НБУ
*/
@Setter
@Getter
@ToString
@AllArgsConstructor
public class CurrencyRateDto {
    private Bank bank;
    private Currency currency;
    private BigDecimal buy;
    private BigDecimal sell;
    private Calendar rateDate; // добавив змінну, яка відповідає за дату останнього реквесту
}
