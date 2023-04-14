package org.example.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.currency.Bank;
import org.example.currency.Currency;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class CurrencyRateDto {
    private Bank bank;
    private Currency currency;
    private BigDecimal buy;
    private BigDecimal sell;
    private Calendar rateDate;

    public String getFormattedRateDate() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy");
        return dateFormat.format(rateDate.getTime());
    }
}
