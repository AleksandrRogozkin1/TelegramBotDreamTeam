package org.example.telegram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.example.currency.Bank;
import org.example.currency.Currency;
import org.example.currency.dto.CurrencyRateDto;
import org.example.services.MonoSendRequest;
import org.example.services.NbuSendRequest;
import org.example.services.PrivatSendRequest;
import org.example.user.User;
import org.example.utils.FileUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CurrencyRateMessageBuilder {
    @Getter
    private static final List<CurrencyRateDto> currencyRateDtoList =
            FileUtils.getListObjectFromJson(FileUtils.getCURRENCY_RATES_FILENAME(), CurrencyRateDto.class);
    @Getter
    private static final List<User> userSettingsDtoList =
            FileUtils.getListObjectFromJson(FileUtils.getUSER_SETTINGS_FILENAME(), User.class);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String getRateByUserSettings(long userId) {
        User user = getUserById(userId);
        Bank bank = user.getCurrentBank();
        List<Currency> currency = user.getCurrentCurrency();
        List<CurrencyRateDto> currencyRate = getCurrencyRatesByBankAndCurrencies(bank, currency);

        if (currencyRate == null || isCurrencyRateOutdated(currencyRate)) updateCurrencyRates();
        return buildCurrencyRateString(user, getCurrencyRatesByBankAndCurrencies(bank, currency));
    }

    public static void updateUserCurrencySettings(long userId, Currency currency) {
        userSettingsDtoList.stream()
                .filter(user -> user.getUserId() == userId)
                .findFirst()
                .ifPresent(user -> {
                    if (!user.getCurrentCurrency().contains(currency)) {
                        user.getCurrentCurrency().add(currency);
                    } else if (user.getCurrentCurrency().size() > 1) {
                        user.getCurrentCurrency().remove(currency);
                    }
                });
        FileUtils.saveInfoToJsonFile(GSON.toJson(userSettingsDtoList), FileUtils.getUSER_SETTINGS_FILENAME());
    }

    public static void updateUserSettings(long userId, Consumer<User> settingsUpdater) {
        userSettingsDtoList.stream()
                .filter(user -> user.getUserId() == userId)
                .forEach(settingsUpdater);
        FileUtils.saveInfoToJsonFile(GSON.toJson(userSettingsDtoList), FileUtils.getUSER_SETTINGS_FILENAME());
    }

    private static String buildCurrencyRateString(User user, List<CurrencyRateDto> currencyRates) {
        int decimalPlaces = user.getDecimalPlaces();
        String header = "Currency from " + currencyRates.get(0).getBank() + "\n" +
                "-----------------------------------------\n";

        String body = currencyRates.stream()
                .map(currencyRate -> currencyRate.getCurrency() + "/UAH:\n"
                        + "Buy: " + String.format("%." + decimalPlaces + "f", currencyRate.getBuy()) + "\n"
                        + "Sell: " + String.format("%." + decimalPlaces + "f", currencyRate.getSell()) + "\n"
                        + "-----------------------------------------\n")
                .collect(Collectors.joining());
        String footer = "Last upd in: " + currencyRates.get(0).getFormattedRateDate();
        return header + body + footer;
    }

    private static void updateCurrencyRates() {
        currencyRateDtoList.clear();
        currencyRateDtoList.addAll(new PrivatSendRequest().getRate());
        currencyRateDtoList.addAll(new NbuSendRequest().getRate());
        currencyRateDtoList.addAll(new MonoSendRequest().getRate());
        FileUtils.saveInfoToJsonFile(GSON.toJson(currencyRateDtoList), FileUtils.getCURRENCY_RATES_FILENAME());
    }

    private static User getUserById(long userId) {
        return userSettingsDtoList.stream()
                .filter(settings -> settings.getUserId() == userId)
                .findFirst()
                .orElse(null);
    }

    private static List<CurrencyRateDto> getCurrencyRatesByBankAndCurrencies(Bank bank, List<Currency> currencies) {
        return currencyRateDtoList.stream()
                .filter(currencyRateDto -> currencyRateDto.getBank().equals(bank) &&
                        currencies.contains(currencyRateDto.getCurrency()))
                .collect(Collectors.toList());
    }

    private static boolean isCurrencyRateOutdated(List<CurrencyRateDto> currencyRates) {
        return System.currentTimeMillis() - currencyRates.get(0).getRateDate().getTimeInMillis() > 300000;
    }
}
