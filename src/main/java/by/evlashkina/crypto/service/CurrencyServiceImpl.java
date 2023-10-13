package by.evlashkina.crypto.service;

import by.evlashkina.crypto.entity.CurrencyDetails;
import by.evlashkina.crypto.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final static String BASE_URL = "https://api.mexc.com/api/v3/ticker/price";

    @Value("${bot.minPriceUpdatePercent}")
    private double minPriceUpdatePercent;

    private final RestTemplate restTemplate = new RestTemplate();
    private final CurrencyRepository currencyRepository;


    @Override
    public List<CurrencyDetails> requestPriceUpdate() {

        List<CurrencyDetails> newCurrencyPrices
                = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CurrencyDetails>>() {
                }
        ).getBody();


        List<CurrencyDetails> currentCurrencyPrices = currencyRepository.findAll();

        if (currentCurrencyPrices.isEmpty()) {
            Optional.ofNullable(newCurrencyPrices).ifPresent(currencyRepository::saveAll);
            log.info("currency prices have been saved");
            return List.of();
        } else {
            List<CurrencyDetails> updatedCurrencyDetails = getChangedCurrencyPrices(
                    Optional.ofNullable(newCurrencyPrices)
                            .orElseGet(() -> currentCurrencyPrices),
                    currentCurrencyPrices);
            Optional.of(updatedCurrencyDetails).ifPresent(currencyRepository::saveAll);
            return updatedCurrencyDetails;
        }
    }

    public List<CurrencyDetails> getChangedCurrencyPrices
            (List<CurrencyDetails> newCurrencyPrices, List<CurrencyDetails> currentCurrencyPrices) {

        return Stream
                .concat(newCurrencyPrices.stream(), currentCurrencyPrices.stream())
                .collect(Collectors.toMap(CurrencyDetails::getSymbol, Function.identity(), (newCurrencyDetail, currentPrices) -> {
                    if (Objects.equals(newCurrencyDetail.getSymbol(), currentPrices.getSymbol())
                            && (Math.abs(newCurrencyDetail.getPrice() - currentPrices.getPrice()) / currentPrices.getPrice()) * 100 > minPriceUpdatePercent) {
                        return newCurrencyDetail;
                    }
                    return null;
                })).values().stream().toList();
    }
}
