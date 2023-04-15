package org.example.services;

import org.example.currency.dto.CurrencyRateDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyRateDto> getRate();
}
